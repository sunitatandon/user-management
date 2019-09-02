package com.mgmt.user.api;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface KycApi {

	boolean uploadKycDocs(String userId, MultipartFile file);

	Resource downloadKycDocs(String userId);
}
