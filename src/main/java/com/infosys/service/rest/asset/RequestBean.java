package com.infosys.service.rest.asset;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RequestBean implements Serializable {

	private static final long serialVersionUID = 4336453529360581815L;
	private String assetId;

	public RequestBean withIccid(String assetId) {
		this.setAssetId(assetId);
		return this;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

}