package org.sis.ancmessaging.web;

import org.joda.time.DateTime;
import org.joda.time.chrono.EthiopicChronology;
import org.joda.time.chrono.GregorianChronology;
import org.sis.ancmessaging.domain.*;
import org.sis.ancmessaging.dto.*;
import org.sis.ancmessaging.enums.EthiopianMonth;
import org.sis.ancmessaging.enums.EthiopianYear;
import org.sis.ancmessaging.json.CustomMotherResponse;
import org.sis.ancmessaging.service.DateTimeService;
import org.sis.ancmessaging.service.HealthPostService;
import org.sis.ancmessaging.service.MotherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
@RequestMapping("/user/mother")
public class MotherController {
	
	@Autowired
	private MotherService motherService;
	
	@Autowired
	private HealthPostService healthPostService;
	
	@Autowired
	private DateTimeService dateTimeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMainPage(Model model) {
		List<Integer> years = EthiopianYear.getYears();
		List<EthiopianMonth> months = EthiopianMonth.getEthiopianMonths();
		model.addAttribute("years", years);
		model.addAttribute("months", months);
		
		List<HealthPost> healthPosts = healthPostService.getAll();
		model.addAttribute("healthPosts", healthPosts);
		MotherDTO motherDTO = new MotherDTO();
		model.addAttribute("motherDTO", motherDTO);
		return "motherindex";
	}
	
	
	@RequestMapping(value = "createmotherajax", method = RequestMethod.POST)
	public @ResponseBody String createMother(@RequestParam("motherId") String motherId, @RequestParam("fullName") String fullName,
			@RequestParam("age") int age, @RequestParam(value = "postId", defaultValue = "0") int postId, @RequestParam(value = "gott", defaultValue = "") String gott, 
			@RequestParam(value = "workerId", defaultValue = "0") int workerId, @RequestParam(value = "lmpYear", defaultValue="0") int year, 
			@RequestParam(value = "lmpMonth", defaultValue = "0") int month, @RequestParam(value = "lmpDay", defaultValue = "0") int day, 
			@RequestParam("edd") String edd, @RequestParam(value = "gestationalAge", defaultValue = "0") int gestationalAge) {
		String result;
        /*
        if (motherService.motherExists(motherId)) {
            result = "Error! MRN already Used!!";
            return result;
        }        */
        Mother mother = new Mother();
		mother.setMotherId(motherId);
		mother.setFullName(fullName);
		mother.setAge(age);
		if (gestationalAge != 0) {
			mother.setGestationalAge(gestationalAge);
			mother.setLmp(null);
		} else {
			DateTime lmpEthiopicDate = new DateTime(year, month, day, 12, 0, EthiopicChronology.getInstance());
			DateTime lmpDate = lmpEthiopicDate.withChronology(GregorianChronology.getInstance());
			mother.setLmp(lmpDate.toDateMidnight().toDate());
			mother.setGestationalAge(null);
		}
		mother.setEdd(dateTimeService.parseAmharicDateString(edd));

        if (postId == 0) {
			mother.setGott(null);
			mother.setHealthWorker(null);
		} else {
			mother.setGott(gott);
			HealthExtensionWorker healthWorker = healthPostService.findHwById(workerId);
			healthWorker.getMothers().add(mother);
			mother.setHealthWorker(healthWorker);
		}
		
		motherService.persist(mother);
        result = "Success";
        return result;
	}
	
	@RequestMapping(value = "getmother", method = RequestMethod.GET)
	public @ResponseBody EditMotherDTO getMotherForEdit(@RequestParam("seqId") int seqId) {
		Mother mother = motherService.findById(seqId);
		EditMotherDTO motherDTO = new EditMotherDTO();
		motherDTO.setFullName(mother.getFullName());
		motherDTO.setSeqId(mother.getSeqId());
		motherDTO.setAge(mother.getAge());
		
		DateTime edd = new DateTime(mother.getEdd()).withChronology(EthiopicChronology.getInstance());
		String eddString = edd.getDayOfMonth() + "/" + edd.getMonthOfYear() + "/" + edd.getYear();
		motherDTO.setEdd(eddString);
		
		DateTime lmp = new DateTime(mother.getLmp()).withChronology(EthiopicChronology.getInstance());
		
		
		motherDTO.setGestationalAge(mother.getGestationalAge());
		motherDTO.setGott(mother.getGott());
		motherDTO.setLmpDay(lmp.getDayOfMonth());
		motherDTO.setLmpMonth(lmp.getMonthOfYear());
		motherDTO.setLmpYear(lmp.getYear());
        motherDTO.setOutcome(mother.getOutcome());
		
		motherDTO.setMotherId(mother.getMotherId());
		
		HealthExtensionWorker healthWorker = mother.getHealthWorker();
		if (healthWorker != null) {
			motherDTO.setWorkerId(healthWorker.getWorkerId());
			motherDTO.setHealthPostId(healthWorker.getHealthPost().getPostId());
		}
		
		return motherDTO;
	}
	
	@RequestMapping(value = "getmothers", method = RequestMethod.GET)
	public @ResponseBody CustomMotherResponse fetchMothers(@RequestParam("postId") int postId, 
			@RequestParam("workerId") int workerId, @RequestParam("status") String status,
			@RequestParam("time") String time, @RequestParam("rows") int rows, @RequestParam("page") int page) {
		CustomMotherResponse response = new CustomMotherResponse();
		//StringBuilder sb = new StringBuilder();
        Map<String, String> sb = new HashMap<String, String> ();
		List<Mother> mothers = motherService.getMothersByCriteria(postId, workerId, status, time, rows, page, sb);
		
		List<MotherDTO> motherDTOs = new ArrayList<MotherDTO>();
		for (Mother mother : mothers) {
            if (!motherService.motherExistsInList(motherDTOs, mother)) {
                MotherDTO mDTO = new MotherDTO();
                mDTO.setSeqId(mother.getSeqId());
                mDTO.setMotherId(mother.getMotherId());
                mDTO.setFullName(mother.getFullName());
                mDTO.setAge(mother.getAge());
                mDTO.setGott(mother.getGott());

                DateTime edd = new DateTime(mother.getEdd()).withChronology(EthiopicChronology.getInstance());
                mDTO.setEdd(edd.getDayOfMonth() + "/" + edd.getMonthOfYear() + "/" + edd.getYear());

                mDTO.setPassed(edd.isBeforeNow());

                mDTO.setGott(mother.getGott());
                HealthExtensionWorker worker = mother.getHealthWorker();
                if (worker != null) {
                    mDTO.setWorkerName(worker.getFullName());
                    mDTO.setWorkerPhone(worker.getPhoneNumber());
                    mDTO.setHealthPost(worker.getHealthPost().getPostName());
                }
                String outcome = mother.getOutcome();
                //Delivery delivery = motherService.getRecentDelivery(mother);

                if (outcome != null) {
                    mDTO.setDeliveryStatus(outcome);
                } else {
                    mDTO.setDeliveryStatus("Not Delivered");
                }

                List<Reminder> reminders = mother.getReminders();
                for(Reminder reminder : reminders) {
                    String reminderType = reminder.getReminderType();
                    String reminderStatus = reminder.getStatus();
                    
                    if (reminderType.equalsIgnoreCase("MONTHLY")) {
                        mDTO.setFirstReminder(true);
                        mDTO.setFirstConfirmation(reminderStatus);
                    } else if (reminderType.equalsIgnoreCase("WEEKLY")) {
                        mDTO.setSecondReminder(true);
                        mDTO.setSecondConfirmation(reminderStatus);
                    }
                }
                motherDTOs.add(mDTO);
            }
		}
		response.setPage(String.valueOf(page));
		response.setRows(motherDTOs);
		response.setTotal(sb.get("totalPages").toString());
		response.setRecords(sb.get("records").toString());
		return response;
	}


	@RequestMapping(value = "geteddfromgestationalage", method = RequestMethod.GET)
	public @ResponseBody String calculateEddFromGestationalAge(@RequestParam("gestationalAge") int gestationalAge) {
		DateTime today = new DateTime();
		int remainingWeeks = 38 - gestationalAge;
		DateTime edd = today.plusWeeks(remainingWeeks);
		DateTime eddEthiopic = edd.withChronology(EthiopicChronology.getInstance());
		return eddEthiopic.getDayOfMonth() + "/" + eddEthiopic.getMonthOfYear() + "/" + eddEthiopic.getYear();
	}
	
	@RequestMapping(value = "editmother", method = RequestMethod.POST)
	public @ResponseBody void editMother(@RequestParam("seqId") int seqId,
			@RequestParam("motherId") String motherId, @RequestParam("fullName") String fullName, 
			@RequestParam("age") int age, @RequestParam(value = "postId", defaultValue = "0") int postId, @RequestParam(value = "gott", defaultValue = "") String gott, 
			@RequestParam(value = "workerId", defaultValue = "0") int workerId, @RequestParam(value = "lmpYear", defaultValue="0") int year, 
			@RequestParam(value = "lmpMonth", defaultValue = "0") int month, @RequestParam(value = "lmpDay", defaultValue = "0") int day, 
			@RequestParam("edd") String edd, @RequestParam(value = "gestationalAge", defaultValue = "0") int gestationalAge, 
			@RequestParam(value = "deliveryStatus", required = false) String deliveryStatus) {
		
		Mother mother = motherService.findById(seqId);
		mother.setMotherId(motherId);
		mother.setFullName(fullName);
		mother.setAge(age);
		if (gestationalAge != 0) {
			mother.setGestationalAge(gestationalAge);
			mother.setLmp(null);
		} else {
			DateTime lmpEthiopicDate = new DateTime(year, month, day, 12, 0, EthiopicChronology.getInstance());
			DateTime lmpDate = lmpEthiopicDate.withChronology(GregorianChronology.getInstance());
			mother.setLmp(lmpDate.toDateMidnight().toDate());
			mother.setGestationalAge(null);
		}
		mother.setEdd(dateTimeService.parseAmharicDateString(edd));
		if (postId == 0) {
			mother.setGott(null);
			mother.setHealthWorker(null);
		} else {
			mother.setGott(gott);
			HealthExtensionWorker healthWorker = healthPostService.findHwById(workerId);
			mother.setHealthWorker(healthWorker);
		}
        if (!deliveryStatus.equals("Not Delivered")) {
            mother.setOutcome(deliveryStatus);
            mother.setArrivalTime(new DateTime().toDate());
        }
		motherService.update(mother);

	}
	
	@RequestMapping(value = "getyearsajax", method = RequestMethod.GET)
	public @ResponseBody List<EthiopianYear> getYearsAjax() {
		List<Integer> years = EthiopianYear.getYears();
		List<EthiopianYear> ethYears = new ArrayList<EthiopianYear>();
		for (Integer year : years) {
			EthiopianYear newYear = new EthiopianYear();
			newYear.setYear(year);
			ethYears.add(newYear);
		}
		return ethYears;
	}
	
	@RequestMapping(value = "getmonthsajax", method = RequestMethod.GET)
	public @ResponseBody List<EthiopianMonth> getMonthsAjax() {
		return EthiopianMonth.getEthiopianMonths();
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
	
	@RequestMapping(value = "getgottsajax", method = RequestMethod.GET)
	public @ResponseBody List<GottDTO> fetchGottsAjax(@RequestParam("postId") int postId) {
		HealthPost healthPost = healthPostService.findById(postId);
		List<Gott> gotts = healthPost.getGotts();
		List<GottDTO> gottDTOs = new ArrayList<GottDTO>();
		for (Gott gott : gotts) {
			GottDTO gDTO = new GottDTO();
			gDTO.setGottId(gott.getGottId());
			gDTO.setGottName(gott.getGottName());
			gDTO.setPostId(postId);
			gottDTOs.add(gDTO);
		}
		
		return gottDTOs;
	}
	
}
