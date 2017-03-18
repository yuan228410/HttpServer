package cn.yuan.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebHandler extends DefaultHandler {
	private List<Entity> entityList;
	private List<Mapping> mappingList;
	private Entity entity;
	private Mapping mapping;
	private String beginTag;
	
	private boolean isMap;
	public List<Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}
	public List<Mapping> getMappingList() {
		return mappingList;
	}
	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}
	@Override
	public void startDocument() throws SAXException {
		entityList=new ArrayList<Entity>();
		mappingList=new ArrayList<Mapping>();
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName!=null)
		{
			beginTag=qName;
			if (qName.equals("servlet")) {
				isMap=false;
				entity=new Entity();
			}else if (qName.equals("servlet-mapping")) {
				isMap=true;
				mapping=new Mapping();
			}
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(beginTag!=null)
		{
			String str=new String(ch, start, length);
			if (isMap) {
				if (beginTag.equals("servlet-name")) {
					mapping.setName(str);
				}else if(beginTag.equals("url-pattern")){
					mapping.getUrlPattern().add(str);
				}
			} else {
				if (beginTag.equals("servlet-name")) {
					entity.setName(str);
				}else if(beginTag.equals("servlet-class")){
					entity.setClz(str);
				}
			}
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName!=null)
		{
			if (qName.equals("servlet")) {
				entityList.add(entity);
			}else if (qName.equals("servlet-mapping")) {
				mappingList.add(mapping);
			}
		
		}
		beginTag=null;
	}
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
}
