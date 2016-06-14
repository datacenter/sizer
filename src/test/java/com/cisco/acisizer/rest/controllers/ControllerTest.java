package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import com.cisco.acisizer.models.Project;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.ui.models.ApplicationTemplate;
import com.cisco.acisizer.utils.ShadowModel;
import com.cisco.acisizer.utils.TestingConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public abstract class ControllerTest {
	public int m_projectId=0;
	public int m_tenantId=0;
	public int m_vrfId=0;
	public int m_l3outId=0;
	public int m_appId=0;
	public int m_epgId=0;
	public int m_bdId=0;
	public int m_sharedResourceId=0;
	
	public final int m_minId = 100;//the project id starts from here
	
	
	@Inject
	public ProjectsRepository projectsRepository;
	@Inject
	public Gson gson;

	public void clearIds()
	{
		m_projectId=0;
		m_tenantId=0;
		m_vrfId=0;
		m_l3outId=0;
		m_appId=0;
		m_epgId=0;
		m_bdId=0;
		m_sharedResourceId=0;
	}
	
	public void createNodes() {
		m_projectId = createProject("proj1");
		
		m_tenantId = createTenant("tenant1",m_projectId);

		m_vrfId = createVrf("vrf1",m_projectId,m_tenantId);

		m_l3outId = createL3out("l3out1",m_projectId,m_tenantId,m_vrfId);
		
		m_appId = createApplication("app1",m_projectId,m_tenantId);
		
		m_bdId = createBd("bd1",m_projectId,m_tenantId,m_vrfId);


		String bdName = null;
		String appName = null;
		
		bdName = getName(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/vrf/"+m_vrfId+"/bds",m_bdId);
		appName = getName(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/apps",m_appId);
		
		m_epgId = createEpg("epg1", appName, m_bdId, m_projectId,m_tenantId,m_appId);
		m_sharedResourceId = createSharedResource("sharedResource1",m_projectId,m_tenantId,m_vrfId);
	}
	
	public List<ShadowModel> getNodes(String queryUrl)
	{
		List<ShadowModel> nodes = new ArrayList<ShadowModel>();
		String resp = when().get(queryUrl).body().asString();
		Type type = new TypeToken<List<ShadowModel>>(){}.getType();
		nodes = gson.fromJson(resp,type );
		
		return nodes;
	}
	public String getName(String url, int id)
	{
		List<ShadowModel> nodes = new ArrayList<ShadowModel>();

		String name = null;
		String resp = when().get(url).body().asString();
		Type type = new TypeToken<List<ShadowModel>>(){}.getType();
		nodes = gson.fromJson(resp,type );
		
		for(ShadowModel iter : nodes)
		{
			if(id == iter.getId())
			{
				name = iter.getName();
				break;
			}
		}

		return name;
	}
	public int createSharedResource(String name, int projectId, int tenantId, int vrfId) {
		
		com.cisco.acisizer.ui.models.SharedResourceUi sr = new com.cisco.acisizer.ui.models.SharedResourceUi();
		sr.setName(name);
		sr.setVrf(Integer.toString(vrfId));
		
		Response respObject = postRequest(sr,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/vrf/"+vrfId+"/sharedResource");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
		
	}

	public int createBd(String name, int projectId, int tenantId,	int vrfId)
	{
		com.cisco.acisizer.ui.models.BdUi bd = new com.cisco.acisizer.ui.models.BdUi();
		bd.setName(name);
		Response respObject = postRequest(bd,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/vrf/"+vrfId+"/bd");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}

	public int createEpg(String epgName, String appName, int bdId, int projectId, int tenantId, int appId) {
		
		com.cisco.acisizer.ui.models.EpgUi epg = new com.cisco.acisizer.ui.models.EpgUi();
		epg.setBdId(bdId);
		epg.setSpan(1);
		epg.setName(epgName);
		epg.setAppId(appId);
		
		Response respObject = postRequest(epg,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/app/"+appId+"/epg");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(epgName));
		respObject.then().body("appId",Matchers.is(appId));
		respObject.then().body("bdId",Matchers.is(bdId));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}

	public int createApplication(String name, int projectId,
			int tenantId) {
		// TODO Auto-generated method stub
		
		com.cisco.acisizer.ui.models.ApplicationUi app = new com.cisco.acisizer.ui.models.ApplicationUi();
		app.setName(name);
		Response respObject = postRequest(app,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/app");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		Type type = new TypeToken<List<ShadowModel>>(){}.getType();

		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}
	
	public int createApplicationTemplate(ApplicationTemplate appTemplate, int projectId, int tenantId)
	{
		Response respObject = postRequest(appTemplate,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/appTemplate");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		//respObject.then().body("displayName",Matchers.is(displayName));

		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}

	public int createL3out(String name, int projectId, int tenantId, int vrfId) {
		// TODO Auto-generated method stub
		com.cisco.acisizer.ui.models.L3outUi l3 = new com.cisco.acisizer.ui.models.L3outUi();
		l3.setName(name);
		Response respObject = postRequest(l3,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/vrf/"+vrfId+"/l3Out");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
		
	}
	public int createVrf(String name, int projectId, int tenantId) {
		// TODO Auto-generated method stub
		com.cisco.acisizer.ui.models.VrfUi vrf = new com.cisco.acisizer.ui.models.VrfUi();
		vrf.setName(name);
		Response respObject = postRequest(vrf,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant/"+tenantId+"/vrf");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
		
	}
	public int createTenant(String name, int projectId)
	{
		Tenant tenant = new Tenant(1,name);
		Response respObject = postRequest(tenant,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenant");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("displayName",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}
	
	public int createTenantTemplate(String displayName, String model,int projectId)
	{
		Tenant template = new Tenant(1,displayName);
		template.setCount(2);
		template.setLocalVrf(true);
		//template.setName(displayName);
		//template.setTemplateType("tenant");
		
		Response respObject = postRequest(template,TestingConstants.PROJECT_BASE_URI+"/"+projectId+"/tenantTemplate");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("displayName",Matchers.is(displayName));
		//respObject.then().body("name",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}

	public int createProject(String projName)
	{
		Project project = new Project();
		project.setName(projName);
		Response respObject = postRequest(project,TestingConstants.PROJECT_BASE_URI);		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(projName));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		ShadowModel obj = gson.fromJson(respObject.asString(),ShadowModel.class);
		return obj.getId();
	}


	public Response postRequest(Object body, String url)
	{
		Response respObject =	given().body(gson.toJson(body)).contentType(ContentType.JSON).when().post(url);
		return respObject;
	}

	public String deleteRequest(String url)
	{
		String resp = when().delete(url).body().asString();
		return resp;
	}
	
	public Response updateRequest(Object body, String url)
	{
		Response respObject =	given().body(gson.toJson(body)).contentType(ContentType.JSON).when().put(url);
		return respObject;
	}
}
