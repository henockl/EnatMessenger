package org.sis.ancmessaging.web;

import java.util.ArrayList;
import java.util.List;

import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.domain.HealthPost;
import org.sis.ancmessaging.domain.Transporter;
import org.sis.ancmessaging.dto.TransporterDTO;
import org.sis.ancmessaging.json.CustomGenericResponse;
import org.sis.ancmessaging.json.CustomTransporterResponse;
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
@RequestMapping("/admin/transporter")
public class TransporterController { // NO_UCD
	@Autowired
	private HealthPostService healthPostService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMainPage(Model model) {
		List<HealthPost> healthPosts = healthPostService.getAll();
		model.addAttribute("healthPosts", healthPosts);
		TransporterDTO transporterDTO = new TransporterDTO();
		model.addAttribute("transporterDTO", transporterDTO);
		return "transporter";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createTransporter(TransporterDTO transporterDTO, BindingResult result, Model model) {
		if (result.hasErrors()) {
			HealthPost healthPost = healthPostService.findById(transporterDTO.getPostId());
			model.addAttribute("healthPost", healthPost);
			List<Gott> gotts = healthPost.getGotts();
			model.addAttribute("gotts", gotts);
			model.addAttribute("transporterDTO", transporterDTO);
			return "/admin/healthpost/entries?pid=" + transporterDTO.getPostId();
		} else {
			Transporter transporter = transporterDTO.generateTransporter();
			transporter.setPhoneNumber("+251" + transporter.getPhoneNumber());
			HealthPost healthPost = healthPostService.findById(transporterDTO.getPostId());
			healthPostService.addTransporterToHp(transporter, healthPost);
			return "redirect:/admin/healthpost/entries?pid=" + transporterDTO.getPostId();
		}
		
	}
	
	@RequestMapping(value = "gettransporters", method = RequestMethod.GET)
	public @ResponseBody CustomTransporterResponse fetchTransporters(@RequestParam("postId") int postId,
			@RequestParam("page") int page, @RequestParam("rows") int rows) {
		
		CustomTransporterResponse response = new CustomTransporterResponse();
		StringBuilder sb = new StringBuilder();
		
		List<Transporter> transporters = healthPostService.getTransportersForHealthPost(postId, rows, page, sb);
		List<TransporterDTO> transporterDTOs = new ArrayList<TransporterDTO>();
		for (Transporter transporter : transporters) {
			TransporterDTO tDTO = new TransporterDTO();
			tDTO.setTransporterId(transporter.getTransporterId());
			tDTO.setFullName(transporter.getFullName());
			tDTO.setPhoneNumber(transporter.getPhoneNumber());
			tDTO.setPostId(postId);
			transporterDTOs.add(tDTO);
		}
		response.setPage(String.valueOf(page));
		response.setRows(transporterDTOs);
		response.setTotal(sb.toString());
		response.setRecords(String.valueOf(transporterDTOs.size()));
		return response;
	}
	
	@RequestMapping(value = "edittransporter", method = RequestMethod.POST)
	public @ResponseBody CustomGenericResponse editGott(
		@RequestParam("transporterId") int transporterId, @RequestParam("fullName") String fullName,
		@RequestParam("phoneNumber") String phoneNumber) {
		
		Transporter transporter = healthPostService.findTransporterById(transporterId);
		transporter.setFullName(fullName);
		transporter.setPhoneNumber(phoneNumber);
		boolean result = healthPostService.persistTransporter(transporter);
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
