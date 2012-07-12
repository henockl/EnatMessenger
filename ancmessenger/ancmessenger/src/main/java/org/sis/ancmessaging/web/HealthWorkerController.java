package org.sis.ancmessaging.web;

import java.util.ArrayList;
import java.util.List;

import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.domain.HealthExtensionWorker;
import org.sis.ancmessaging.domain.HealthPost;
import org.sis.ancmessaging.dto.HealthWorkerDTO;
import org.sis.ancmessaging.json.CustomGenericResponse;
import org.sis.ancmessaging.json.CustomHealthWorkerResponse;
import org.sis.ancmessaging.service.HealthPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/healthworker")
public class HealthWorkerController { // NO_UCD
	@Autowired
	private HealthPostService healthPostService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMainPage(Model model) {
		List<HealthPost> healthPosts = healthPostService.getAll();
		model.addAttribute("healthPosts", healthPosts);
		HealthWorkerDTO healthWorkerDTO = new HealthWorkerDTO();
		model.addAttribute("healthWorkerDTO", healthWorkerDTO);
		return "healthworker";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createWorker(HealthWorkerDTO healthWorkerDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			HealthPost healthPost = healthPostService.findById(healthWorkerDTO.getPostId());
			model.addAttribute("healthPost", healthPost);
			List<Gott> gotts = healthPost.getGotts();
			model.addAttribute("gotts", gotts);
			model.addAttribute("healthWorkerDTO", healthWorkerDTO);
			return "/admin/healthpost/entries?pid=" + healthWorkerDTO.getPostId();
		} else {
			HealthExtensionWorker healthWorker = healthWorkerDTO.generateHealthWorker();
			healthWorker.setPhoneNumber("+251" + healthWorker.getPhoneNumber());
			HealthPost healthPost = healthPostService.findById(healthWorkerDTO.getPostId());
			healthPostService.addHwToHp(healthWorker, healthPost);
			return "redirect:/admin/healthpost/entries?pid=" + healthWorkerDTO.getPostId();
		}
		
	}
	
	@RequestMapping(value = "createajax", method = RequestMethod.POST)
	public void createGott(@RequestParam("postId") int postId, @RequestParam("fullName") String fullName, 
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("sex") char sex) {
		
		HealthExtensionWorker healthWorker = new HealthExtensionWorker();
		healthWorker.setFullName(fullName);
		healthWorker.setPhoneNumber("+251" + phoneNumber);
		healthWorker.setSex(sex);
		
		HealthPost healthPost = healthPostService.findById(postId);
		
		healthPostService.addHwToHp(healthWorker, healthPost);
	}
	
	@RequestMapping(value = "gethealthworkers", method = RequestMethod.GET)
	public @ResponseBody CustomHealthWorkerResponse fetchHealthWorkers(@RequestParam("postId") int postId,
			@RequestParam("rows") int rows, @RequestParam("page") int page) {
		CustomHealthWorkerResponse response = new CustomHealthWorkerResponse();
		//HealthPost healthPost = healthPostService.findById(postId);
		StringBuilder sb = new StringBuilder();
		List<HealthExtensionWorker> healthWorkers = healthPostService.getHealthWorkersForHealthPost(postId, rows, page, sb);
		List<HealthWorkerDTO> healthWorkerDTOs = new ArrayList<HealthWorkerDTO>();
		for (HealthExtensionWorker healthWorker : healthWorkers) {
			HealthWorkerDTO hDTO = new HealthWorkerDTO();
			hDTO.setWorkerId(healthWorker.getWorkerId());
			hDTO.setFullName(healthWorker.getFullName());
			hDTO.setPhoneNumber(healthWorker.getPhoneNumber());
			hDTO.setPostId(postId);
			hDTO.setSex(healthWorker.getSex());
			healthWorkerDTOs.add(hDTO);
		}
		response.setPage(String.valueOf(page));
		response.setRows(healthWorkerDTOs);
		response.setTotal(sb.toString());
		response.setRecords(String.valueOf(healthWorkerDTOs.size()));
		return response;
	}
	
	@RequestMapping(value = "gethealthworkersajax", method = RequestMethod.GET)
	public @ResponseBody List<HealthWorkerDTO> fetchHealthWorkersAjax(@RequestParam("postId") int postId) {
		HealthPost healthPost = healthPostService.findById(postId);
		List<HealthExtensionWorker> healthWorkers = healthPost.getHealthWorkers();
		List<HealthWorkerDTO> healthWorkerDTO = new ArrayList<HealthWorkerDTO>();
		for (HealthExtensionWorker healthWorker : healthWorkers) {
			HealthWorkerDTO hDTO = new HealthWorkerDTO();
			hDTO.setWorkerId(healthWorker.getWorkerId());
			hDTO.setFullName(healthWorker.getFullName());
			hDTO.setPostId(postId);
			healthWorkerDTO.add(hDTO);
		}
		
		return healthWorkerDTO;
	}
	
	@RequestMapping(value = "edithealthworker", method = RequestMethod.POST)
	public @ResponseBody CustomGenericResponse editHealthWorker(
		@RequestParam("workerId") int workerId, @RequestParam("fullName") String fullName, @RequestParam("sex") char sex,
		@RequestParam("phoneNumber") String phoneNumber) {
		
		HealthExtensionWorker healthWorker = healthPostService.findHwById(workerId);
		healthWorker.setFullName(fullName);
		healthWorker.setPhoneNumber(phoneNumber);
		healthWorker.setSex(sex);
		boolean result = healthPostService.persistHealthWorker(healthWorker);
		CustomGenericResponse response = new CustomGenericResponse();
		
		if (result) {
			response.setMessage("Action Successful");
			response.setSuccess(true);
		} else {
			response.setMessage("Action Failed");
			response.setSuccess(false);
		}
		return response;
	}
}
