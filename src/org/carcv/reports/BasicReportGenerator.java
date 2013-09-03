/**
 * 
 */
package org.carcv.reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.carcv.model.CarData;
import org.carcv.model.Entry;
import org.carcv.model.IAddress;


/**
 * @author oskopek
 *
 */
public class BasicReportGenerator {
	
	public static void buildPDFReport(Entry e, String templateFilename, String exportToFilename, String reportBuilderLocation, String reportName) {
			
		Map<String, Object> values = new HashMap<String, Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		CarData data = e.getData();
		

		DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy"); 
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		
		//report
		parameters.put("reportid", Long.toString(System.currentTimeMillis()));
		parameters.put("reportname", reportName);
		parameters.put("reportlocation", reportBuilderLocation);
		parameters.put("reportdate", dateFormat.format(new Date(System.currentTimeMillis())));
		
		//data
		IAddress add = (IAddress) data.getLocation();
		String dataLocation = add.street() + " " + add.streetNumber() + "/" + add.referenceNumber() + "\n"
				+ add.postalcode() + " " + add.city() + "\n"
				+ add.country();
		
		//parameters.put("id", Long.toString(data.getId()));
		parameters.put("date", dateFormat.format(data.getTimestamp()));
		parameters.put("location", dataLocation);
		parameters.put("LPNumber", data.getLicencePlate().getText());
		parameters.put("videoURL", data.getVideo().getURL());
		parameters.put("time", timeFormat.format(data.getTimestamp()));
		parameters.put("speed", Double.toString(data.getSpeed().getSpeed()) + " " + data.getSpeed().getUnit().toString());
		
		//parameters.put
		
		Collection<Map<String, ?>> mapList = new ArrayList<Map<String, ?>>();
		mapList.add(values);
		
		JRMapCollectionDataSource mapDataSource = new JRMapCollectionDataSource(mapList);
		
		
		try {
			//compile template - already precompiled
			//JasperCompileManager.compileReportToFile(templateFilename + ".jrxml", templateFilename + ".jasper");
			
			//fill with data
			JasperPrint print = JasperFillManager.fillReport(templateFilename, parameters, mapDataSource);
			
			//export
			JRExporter exporter = new JRPdfExporter();
			
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, exportToFilename);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			
			exporter.exportReport();
			
		} catch (JRException jre) {
			jre.printStackTrace();
		}
		
		
		return;
	}

}
