package cm.yuan.xml;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//1.解析工厂
		SAXParserFactory factory=SAXParserFactory.newInstance();
		//2.析工厂获取默认解析器
		SAXParser parser=factory.newSAXParser();
		//3.文档注册处理器
		//4.编写处理器
		PersonHandle personHandle=new PersonHandle();
		parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("cm/yuan/xml/person.xml"),
				personHandle);
		List<Person> persons=personHandle.getPersons();
		for(Person person:persons)
		{
			System.out.println(person.getName()+"--->"+person.getAge());
		}
		
	}

}
