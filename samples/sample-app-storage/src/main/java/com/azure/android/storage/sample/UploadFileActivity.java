package com.azure.android.storage.sample;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.NetworkType;

import com.azure.android.core.credential.TokenRequestObservable;
import com.azure.android.core.credential.TokenRequestObservableAuthInterceptor;
import com.azure.android.core.credential.TokenRequestObserver;
import com.azure.android.core.credential.TokenResponseCallback;
import com.azure.android.storage.blob.StorageBlobAsyncClient;
import com.azure.android.storage.sample.config.StorageConfiguration;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.azure.android.storage.sample.Constants.FILE_URI_EXTRA;

public class UploadFileActivity extends AppCompatActivity {
    private static final String TAG = UploadFileActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private StorageConfiguration storageConfiguration;

    // Singleton StorageBlobClient that will be created by Dagger. The singleton object is shared across various
    // activities in the application.
    @Inject
    StorageBlobAsyncClient storageBlobAsyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uploadfile);

        // Request Dagger to get singleton StorageBlobClient and initialize this.storageBlobClient
        ((MainApplication) getApplication()).getAppComponent().inject(this);

        this.progressBar = findViewById(R.id.progressBar);
        this.storageConfiguration = StorageConfiguration.create(getApplicationContext());

        // Set up Login
        final List<String> blobEndpointScopes = Collections.singletonList(storageBlobAsyncClient.getBlobServiceUrl() + ".default");
        TokenRequestObservableAuthInterceptor authInterceptor =
            new TokenRequestObservableAuthInterceptor(blobEndpointScopes);

        TokenRequestObservable tokenRequestObservable = authInterceptor.getTokenRequestObservable();
        LifecycleOwner lifecycleOwner = this;

        PublicClientApplication.createMultipleAccountPublicClientApplication(
            this.getApplicationContext(),
            R.raw.authorization_configuration,
            new PublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                @Override
                public void onCreated(IMultipleAccountPublicClientApplication application) {
                    tokenRequestObservable.observe(lifecycleOwner, new TokenRequestObserver() {
                        @Override
                        public void onTokenRequest(String[] scopes, TokenResponseCallback callback) {
                            MsalClient.signIn(application, getActivity(), scopes, callback);
                        }
                    });
                }

                @Override
                public void onError(MsalException exception) {
                    Log.e(TAG, "Exception found when trying to sign in.", exception);
                }
            });

        // Create a new StorageBlobClient from the existing client with different base URL and credentials but sharing
        // the underlying OkHttp Client.
        storageBlobAsyncClient = storageBlobAsyncClient
            .newBuilder("com.azure.android.storage.sample.upload")
            .setBlobServiceUrl(storageConfiguration.getBlobServiceUrl())
            .setCredentialInterceptor(authInterceptor)
            .setTransferRequiredNetworkType(NetworkType.CONNECTED)
            .build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri fileUri = getIntent().getParcelableExtra(FILE_URI_EXTRA);
        // Use content resolver to get file size and name.
        // https://developer.android.com/training/secure-file-sharing/retrieve-info
        Cursor cursor =
            getContentResolver().query(fileUri, null, null, null, null);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

        cursor.moveToFirst();
        final long fileSize = cursor.getLong(sizeIndex);
        final String blobName = cursor.getString(nameIndex);

        final String containerName = storageConfiguration.getContainerName();

        this.progressBar.setMax((int) fileSize);


        Log.d("Upload Content", "Content Uri: " + fileUri);
        Log.d("Upload Content", "Blob name: " + blobName);
        Log.d("Upload Content", "File size: " + fileSize);

        try {
            storageBlobAsyncClient.upload(getApplicationContext(), containerName, blobName, false, fileUri)
                .observe(this, new TransferObserver() {
                    @Override
                    public void onStart(long transferId) {
                        Log.i(TAG, "onStart()");
                    }

                    public void onProgress(long transferId, long totalBytes, long bytesTransferred) {
                        Log.i(TAG, "onProgress(" + totalBytes + ", " + bytesTransferred + ")");

                        progressBar.setProgress((int) bytesTransferred);
                    }

                    public void onSystemPaused(long transferId) {
                        Log.i(TAG, "onSystemPaused()");
                    }

                    public void onResume(long transferId) {
                        Log.i(TAG, "onResumed()");
                    }

                    public void onComplete(long transferId) {
                        Log.i(TAG, "onCompleted()");

                        progressBar.setProgress((int) fileSize);

                        Toast.makeText(getApplicationContext(), "Upload complete", Toast.LENGTH_SHORT).show();
                    }

                    public void onError(long transferId, String errorMessage) {
                        Log.i(TAG, "onError() -> : " + errorMessage);
                    }
                });
        } catch (Exception ex) {
            Log.e(TAG, "Upload submit failed: ", ex);
        }
    }

    private Activity getActivity() {
        return this;
    }
}
