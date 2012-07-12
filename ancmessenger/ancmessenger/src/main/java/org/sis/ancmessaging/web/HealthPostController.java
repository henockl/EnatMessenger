package org.sis.ancmessaging.web;

import java.util.ArrayList;
import java.util.List;

import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.domain.HealthCenter;
import org.sis.ancmessaging.domain.HealthPost;
import org.sis.ancmessaging.dto.GottDTO;
import org.sis.ancmessaging.dto.HealthPostDTO;
import org.sis.ancmessaging.dto.HealthWorkerDTO;
import org.sis.ancmessaging.dto.TransporterDTO;
import org.sis.ancmessaging.json.CustomGenericResponse;
import org.sis.ancmessaging.json.CustomHealthPostResponse;
import org.sis.ancmessaging.service.HealthCenterService;
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
@RequestMapping("/admin/healthpost")
public class HealthPostController { // NO_UCD
	@Autowired
	private HealthPostService healthPostService;
	
	@Autowired
	private HealthCenterService healthCenterService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMainPage(@RequestParam("pid") int pId, Model model) {
		HealthCenter healthCenter = healthCenterService.findById(pId);
		model.addAttribute("healthCenter", healthCenter);
		HealthPostDTO healthPostDTO = new HealthPostDTO();
		healthPostDTO.setCenterId(pId);
		model.addAttribute("healthPostDTO", healthPostDTO);
		return "healthpost";
	}
	
	@RequestMapping(value = "entries", method = RequestMethod.GET)
	public String getEntriesPage(@RequestParam("pid") int postId, Model model) {
		HealthPost healthPost = healthPostService.findById(postId);
		model.addAttribute("healthPost", healthPost);
		List<Gott> gotts = healthPost.getGotts();
		model.addAttribute("gotts", gotts);
		
		GottDTO gottDTO = new GottDTO();
		gottDTO.setPostId(postId);
		
		HealthWorkerDTO healthWorkerDTO = new HealthWorkerDTO();
		healthWorkerDTO.setPostId(postId);
		
		TransporterDTO transporterDTO = new TransporterDTO();
		transporterDTO.setPostId(postId);
		
		model.addAttribute("gottDTO", gottDTO);
		model.addAttribute("healthWorkerDTO", healthWorkerDTO);
		model.addAttribute("transporterDTO", transporterDTO);
		model.addAttribute("gotts", gotts);
		
		return "healthpostentries";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createPost(HealthPostDTO healthPostDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("healthCenter", healthCenterService.findById(healthPostDTO.getCenterId()));
			model.addAttribute("healthPostDTO", healthPostDTO);
			return "healthpost";
		} else {
			HealthPost healthPost = healthPostDTO.generateHealthPost();
			HealthCenter healthCenter = healthCenterService.findById(healthPostDTO.getCenterId());
			healthCenterService.addHpToHc(healthCenter, healthPost);
			return "redirect:/admin/healthpost?pid=" + healthPostDTO.getCenterId();
		}
		
	}
	
	@RequestMapping(value = "gethealthposts", method = RequestMethod.GET)
	public @ResponseBody CustomHealthPostResponse fetchHealthPosts(@RequestParam("centerId") int centerId,
			@RequestParam("rows") int rows, @RequestParam("page") int page) {
		CustomHealthPostResponse response = new CustomHealthPostResponse();
		//HealthCenter healthCenter = healthCenterService.findById(centerId);
		StringBuilder sb = new StringBuilder();
		List<HealthPost> healthPosts = healthPostService.getHealthPostsForCenter(centerId, rows, page, sb);
		List<HealthPostDTO> hps = new ArrayList<HealthPostDTO>();
		for (HealthPost hp : healthPosts) {
			HealthPostDTO healthPost = new HealthPostDTO();
			healthPost.setPostId(hp.getPostId());
			healthPost.setPostName(hp.getPostName());
			healthPost.setKebele(hp.getKebele());
			healthPost.setCenterId(centerId);
			hps.add(healthPost);
		}
		response.setPage(String.valueOf(page));
		response.setRows(hps);
		response.setTotal(sb.toString());
		response.setRecords(String.valueOf(hps.size()));
		return response;
	}
	
	@RequestMapping(value = "gethealthpostsajax", method = RequestMethod.GET)
	public @ResponseBody List<HealthPostDTO> getHealthPostsAjax() {
		List<HealthPost> healthPosts = healthPostService.getAll();
		List<HealthPostDTO> hpDTO = new ArrayList<HealthPostDTO>(); 
		for (HealthPost healthPost : healthPosts) {
			HealthPostDTO dto = new HealthPostDTO();
			dto.setPostId(healthPost.getPostId());
			dto.setPostName(healthPost.getPostName());
			dto.setKebele(healthPost.getKebele());
			hpDTO.add(dto);
		}
		return hpDTO;
	}
	
	@RequestMapping(value = "edithealthpost", method = RequestMethod.POST)
	public @ResponseBody CustomGenericResponse editPost(
		@RequestParam("postId") int postId,	@RequestParam("postName") String postName,
		@RequestParam("kebele") String kebele) {
		
		HealthPost healthPost = healthPostService.findById(postId);
		healthPost.setPostName(postName);
		healthPost.setKebele(kebele);
		
		boolean result = healthPostService.persist(healthPost);
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
