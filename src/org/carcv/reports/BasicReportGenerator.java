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
		CarData data = e.getData();
		

		DateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy"); 
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		
		//report
		values.put("reportid", Long.toString(System.currentTimeMillis()));
		values.put("reportname", reportName);
		values.put("reportlocation", reportBuilderLocation);
		values.put("reportdate", dateFormat.format(new Date(System.currentTimeMillis())));
		
		//data
		IAddress add = (IAddress) data.getLocation();
		String dataLocation = add.street() + " " + add.streetNumber() + "/" + add.referenceNumber() + "\n"
				+ add.postalcode() + " " + add.city() + "\n"
				+ add.country();
		
		//values.put("id", Long.toString(data.getId()));
		values.put("date", dateFormat.format(data.getTimestamp()));
		values.put("location", dataLocation);
		values.put("LPNumber", data.getLicencePlate().getText());
		values.put("videoURL", data.getVideo().getURL());
		values.put("time", timeFormat.format(data.getTimestamp()));
		values.put("speed", Double.toString(data.getSpeed().getSpeed()) + " " + data.getSpeed().getUnit().toString());
		
		//values.put
		
		Collection<Map<String, ?>> mapList = new ArrayList<Map<String, ?>>();
		mapList.add(values);
		
		JRMapCollectionDataSource mapDataSource = new JRMapCollectionDataSource(mapList);
		
		Map parameters = new HashMap();
		
		
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
