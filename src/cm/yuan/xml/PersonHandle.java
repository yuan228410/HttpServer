package cm.yuan.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 存储对象
 * @author yuanzhixiangsuse
 *
 */
public class PersonHandle extends DefaultHandler{
	private List<Person>persons;
	private Person person;
	private String tag;//记录标签名
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	@Override
	public void startDocument() throws SAXException {
		System.out.println("处理文档开始");
		persons=new ArrayList<>();
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("开始一个元素");
		if (qName!=null) {
			tag=qName;
		}
		if (qName!=null && qName.equals("person")) {
			person=new Person();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//System.out.println(new String(ch,start,length));
		if(null!=tag && tag.equals("name")){
			person.setName(new String(ch,start,length));
		}
		if(null!=tag && tag.equals("age")){
			Integer age=Integer.valueOf(new String(ch,start,length));
			person.setAge(age);
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("结束一个元素");
		if(qName.equals("person"))
		{
			this.persons.add(person);
		}
		tag=null;
	}
	@Override
	public void endDocument() throws SAXException {
		System.out.println("处理文档结束");
	}

	


}
