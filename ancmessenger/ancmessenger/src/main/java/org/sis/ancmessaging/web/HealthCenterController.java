package org.sis.ancmessaging.web;


import java.util.ArrayList;
import java.util.List;

import org.sis.ancmessaging.domain.HealthCenter;
import org.sis.ancmessaging.dto.HealthCenterDTO;
import org.sis.ancmessaging.json.CustomGenericResponse;
import org.sis.ancmessaging.json.CustomHealthCenterResponse;
import org.sis.ancmessaging.service.HealthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/healthcenter")
public class HealthCenterController {
	@Autowired
	private HealthCenterService healthCenterService;
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String getMainPage(@PathVariable int id, Model model) {
		HealthCenter healthCenter = healthCenterService.findById(id);
		String telephone = healthCenter.getCenterPhone().substring(4);
		healthCenter.setCenterPhone(telephone);
		model.addAttribute("healthCenter", healthCenter);
		return "healthcenter";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createCenter(HealthCenter healthCenter, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("healthCenter", healthCenter);
			//model.addAttribute("healthCenters", healthCenterService.getAllHealthCenters());
			return "healthcenter";
		} else {
			healthCenter.setCenterPhone("+251" + healthCenter.getCenterPhone());
			healthCenterService.persist(healthCenter);
			return "redirect:/admin/healthpost?pid=" + healthCenter.getCenterId();
		}
	}
	
	
	@RequestMapping(value = "gethealthcenters", method = RequestMethod.GET)
	public @ResponseBody CustomHealthCenterResponse fetchHealthCenters() {
		CustomHealthCenterResponse response = new CustomHealthCenterResponse();
		List<HealthCenter> healthCenters = healthCenterService.getAllHealthCenters();
		List<HealthCenterDTO> hcs = new ArrayList<HealthCenterDTO>();
		for (HealthCenter hc : healthCenters) {
			HealthCenterDTO dto = new HealthCenterDTO();
			dto.setCenterId(hc.getCenterId());
			dto.setCenterName(hc.getCenterName());
			dto.setCenterPhone(hc.getCenterPhone());
			hcs.add(dto);
		}
		response.setPage("1");
		response.setRows(hcs);
		response.setTotal("10");
		response.setRecords(String.valueOf(healthCenters.size()));
		return response;
	}
	
	@RequestMapping(value = "edithealthcenter", method = RequestMethod.POST)
	public @ResponseBody CustomGenericResponse editCenter(
		@RequestParam("centerId") int centerId,	@RequestParam("centerName") String centerName,
		@RequestParam("centerPhone") String centerPhone) {
		
		HealthCenter healthCenter = new HealthCenter();
		healthCenter.setCenterId(centerId);
		healthCenter.setCenterName(centerName);
		healthCenter.setCenterPhone(centerPhone);
		
		boolean result = healthCenterService.persist(healthCenter);
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
