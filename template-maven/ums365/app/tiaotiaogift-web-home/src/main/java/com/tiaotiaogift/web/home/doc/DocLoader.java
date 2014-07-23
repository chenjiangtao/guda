package com.tiaotiaogift.web.home.doc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocLoader {

	private static String docConfigPath = "spring/doc.xml";

	private static Map<String, Doc> docs =  new HashMap<String, Doc>();
	
	private static Map<String, List<Doc>> docTypes = 
	 new HashMap<String, List<Doc>>();
	
	static {
		try {
			loadDocs();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Doc> getDocs() {
		return docs;
	}
	
	public static Map<String, List<Doc>> getTypes() {
		return docTypes;
	}

	public static void loadDocs()
			throws ParserConfigurationException, SAXException, IOException {
		ClassLoader currentClassLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream inputStream = currentClassLoader
				.getResourceAsStream(docConfigPath);
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputStream);
		NodeList nodeList = doc.getChildNodes();


		filterNode(nodeList.item(0).getChildNodes());


	}

	public static void filterNode(NodeList nodeList) {

		if (nodeList == null) {
			return;
		}
		for (int j = 0, lenJ = nodeList.getLength(); j < lenJ; ++j) {
			Node node = nodeList.item(j);
			if (node.hasAttributes()) {
				NamedNodeMap namedNodeMap = node.getAttributes();
				String id = namedNodeMap.getNamedItem("id").getNodeValue();
				String title = namedNodeMap.getNamedItem("title").getNodeValue();
				String type = namedNodeMap.getNamedItem("type").getNodeValue();
				
				Doc d = new Doc();
				d.setId(id);
				d.setTitle(title);
				d.setType(type);
				List<Doc> l = docTypes.get(type);
				if(l == null){
					l = new ArrayList<Doc>();
	
					docTypes.put(type, l);
				}
				l.add(d);
				if (node.hasChildNodes()
						&& node.getChildNodes().getLength() > 0) {
					NodeList childNodeList = node.getChildNodes();
					List<Content> list = new ArrayList<Content>();
					for (int i = 0, leni = childNodeList.getLength(); i < leni; ++i) {
						Node childnode = childNodeList.item(i);
						NodeList t = childnode.getChildNodes();
						if (t.getLength() > 0) {
							Content c =null;
								c = new Content();	
							for (int m = 0, lenm = t.getLength(); m < lenm; ++m) {
								
								Node t1 = t.item(m);
								
								if ("title".equals(t1.getNodeName())) {
									c.setH(t1.getTextContent());
								}
								if ("content".equals(t1.getNodeName())) {
									c.setDetail(t1.getTextContent());
								}
								

							}
							list.add(c);
						}
					}
					d.setContents(list);
					if (list.size() > 0) {
						docs.put(id, d);
					}
				}

			}

		}

	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {

		try {
			loadDocs();
			System.out.println(DocLoader.getTypes().get("knowlege").size());
			Set<String> set = DocLoader.getDocs().keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {

				String code = it.next();
				System.out.println("........"+code);
				Doc mm = DocLoader.getDocs().get(code);

					System.out.println(mm.getTitle());
					System.out.println(mm.getId());
					System.out.println(mm.getType());
					System.out.println(mm.getContents().size());
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
