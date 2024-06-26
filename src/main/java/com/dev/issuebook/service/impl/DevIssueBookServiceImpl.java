package com.dev.issuebook.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.issuebook.constant.Keys;
import com.dev.issuebook.db.repository.IssueRepository;
import com.dev.issuebook.entity.IssueEntity;
import com.dev.issuebook.service.DevIssueBookService;

@Service
public class DevIssueBookServiceImpl implements DevIssueBookService {

	Logger log = LoggerFactory.getLogger(DevIssueBookServiceImpl.class);

	@Autowired
	IssueRepository issueRepo;

	@Override
	public List<Object> listIssuesByUser(int userId) {
		return getJsonArrayByUser(userId).toList();
	}

	@Override
	public void saveIssueByUser(JSONObject issueJson, int userId) {

		Optional<IssueEntity> entity = issueRepo.findByUserId(userId);

		if (entity.isPresent()) {
			IssueEntity existedEntity = entity.get();
			JSONArray details = existedEntity.getDetails();
			details.put(issueJson);
			issueRepo.save(existedEntity);

			log.info("Added new issue in EXISTING list of userId: " + userId);

		} else {
			JSONArray details = new JSONArray();
			details.put(issueJson);

			IssueEntity issueEntity = new IssueEntity();
			issueEntity.setDetails(details);
			issueEntity.setUserId(userId);
			issueRepo.save(issueEntity);

			log.info("Added new issue in NEW list of userId: " + userId);
		}
	}

	@Override
	public void updateIssue(JSONObject issueJson, String id, int userId) {

		Optional<IssueEntity> entity = issueRepo.findByUserId(userId);
		if (entity.isPresent()) {
			IssueEntity existedEntity = entity.get();
			JSONArray details = removeJsonObjectById(existedEntity.getDetails(), id);
			details.put(issueJson);
			existedEntity.setDetails(details);
			issueRepo.save(existedEntity);
		}
	}

	@Override
	public JSONObject getIssueById(String id, int userId) {

		JSONObject issueDetails = new JSONObject();
		Optional<IssueEntity> entity = issueRepo.findByUserId(userId);

		if (entity.isPresent()) {
			issueDetails = findJsonInArray(entity.get().getDetails(), id);
		}
		log.info("Found issue for userId: " + userId + " and id: " + id);
		return issueDetails;
	}

	@Override
	public void deleteIssueById(String id, int userId) {
		Optional<IssueEntity> entity = issueRepo.findByUserId(userId);
		if (entity.isPresent()) {
			IssueEntity existedEntity = entity.get();
			// to test
			existedEntity.setDetails(removeJsonObjectById(existedEntity.getDetails(), id));
			issueRepo.save(existedEntity);
		}
	}

	private JSONArray getJsonArrayByUser(int userId) {

		Optional<IssueEntity> entity = issueRepo.findByUserId(userId);
		if (entity.isPresent()) {
			log.info("Returning issue list from db for user: " + userId);
			return entity.get().getDetails();
		}
		return new JSONArray();
	}

	private JSONObject findJsonInArray(JSONArray jsonArray, String id) {
		Iterator<Object> itr = jsonArray.iterator();
		while (itr.hasNext()) {
			JSONObject json = (JSONObject) itr.next();

			if (id.equals(json.getString(Keys.ID.getVal()))) {
				return json;
			}
		}
		return new JSONObject();
	}

	private JSONArray removeJsonObjectById(JSONArray issueArray, String id) {
		int counter = 0;
		Iterator<Object> itr = issueArray.iterator();
		while (itr.hasNext()) {
			JSONObject json = (JSONObject) itr.next();
			if (id.equals(json.getString(Keys.ID.getVal()))) {
				issueArray.remove(counter);
				break;
			}
			counter++;
		}
		return issueArray;
	}
}
