package com.cargo_signal.blobstorage;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Azure Blob Storage support.
 */
public class BlobStorageManager {

  private String azureConnectionString = null;
  private ExecutionContext context = null;
  private CloudStorageAccount storageAccount = null;

  public BlobStorageManager(ExecutionContext context) throws Exception {
    azureConnectionString = System.getenv("BI_CONNECTOR_CONNECTION_STRING");
    this.context = context;

    try {
      this.storageAccount = CloudStorageAccount.parse(azureConnectionString);
    } catch (Exception ex) {
      context.getLogger().severe("Unable to connect to storage account.  Exception: " + ex);
      throw ex;
    }
  }
  
  public void createContainer(String name) throws Exception {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("name parameter must be non-null and not empty");
    }

    try {
      CloudBlobClient blobClient = createBlobClient();
      CloudBlobContainer container = blobClient.getContainerReference(name);
      container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
      context.getLogger().info("Created container " + name);
    } catch (StorageException ex) {
      context.getLogger().severe("Failed to create container.  StorageException: " + ex);
      throw ex;
    } catch (Exception ex) {
      context.getLogger().severe("Failed to create container.  Exception: " + ex);
      throw ex;
    }
  }

  public void createBlob(String containerName, String blobName, String data) throws Exception {
    InputStream targetStream = null;

    context.getLogger().info("Pushing to container '" + containerName + "', the blob named '" + blobName + "'.");

    try {
      CloudBlobClient blobClient = createBlobClient();
      CloudBlobContainer container = blobClient.getContainerReference(containerName);
      CloudBlockBlob blob = container.getBlockBlobReference(blobName);

      targetStream = org.apache.commons.io.IOUtils.toInputStream(data, Charset.defaultCharset());
      blob.upload(targetStream, data.length());
    } catch (Exception ex) {
      context.getLogger().severe("Failed to create blob.  Exception: " + ex);
      throw ex;
    } finally {
      if (targetStream != null) {
        targetStream.close();
      }
    }
  }

  /* Remove characters that are not allowed in Azure container name */
  public String sanitizeContainerName(String name) {    
    return (name == null) ? "" : name.replace(":", "").replace(".", "").toLowerCase();
  }

  private CloudBlobClient createBlobClient() {
    return this.storageAccount.createCloudBlobClient();
  }
}
