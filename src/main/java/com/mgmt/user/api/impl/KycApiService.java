package com.mgmt.user.api.impl;

import com.mgmt.user.api.KycApi;
import com.mgmt.user.exception.KycNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

@Service
public class KycApiService implements KycApi {

	private static final Logger log = LoggerFactory.getLogger(KycApiService.class);

	private Path kycDirectory;

	public KycApiService(@Value("${kyc.directory.path}") String kycDirectoryPath) throws IOException {
		kycDirectory = Paths.get(kycDirectoryPath);
		if (!kycDirectory.toFile().exists())
			Files.createDirectories(kycDirectory);
	}

	@Override
	public boolean uploadKycDocs(String userId, MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(format("%s/%s/kyc.txt", kycDirectory, userId));
			if (!path.getParent().toFile().exists())
				Files.createDirectories(path.getParent());
			Files.write(path, bytes);
		} catch (IOException e) {
			log.error(format("Error while uploading kyc document: %s", file.getOriginalFilename()), e);
			return false;
		}
		return true;
	}

	@Override
	public Resource downloadKycDocs(String userId) {
		try {
			Path path = Paths.get(format("%s/%s", kycDirectory, userId));
			Path filePath = path.resolve("kyc.txt").normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new KycNotFoundException("No KYC document found for this user");
			}
		} catch (MalformedURLException ex) {
			throw new KycNotFoundException("No KYC document found for this user");
		}
	}
}
