package com.beini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaController {
	@Autowired
	private DiscoveryClient discoveryClient;
	@GetMapping("/getUserMeta")
	public List<?> getUserMeta(){
		return this.discoveryClient.getInstances("authorization");
	}
}
