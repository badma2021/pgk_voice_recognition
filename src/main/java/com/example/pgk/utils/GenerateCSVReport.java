package com.example.pgk.utils;

import com.example.pgk.model.dto.PartDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.PrintWriter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateCSVReport {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateCSVReport.class);

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void writeUsers(PrintWriter writer, PartDTO[] parts)  {

        try {

            ColumnPositionMappingStrategy mapStrategy
                    = new ColumnPositionMappingStrategy();

            mapStrategy.setType(PartDTO.class);

            String[] columns = new String[]{ "id", "partName", "partNumber", "productionYear", "factoryNumber", "comment",
                    "audioRecordName","createdAt","userId" };
            mapStrategy.setColumnMapping(columns);
            mapStrategy.generateHeader(columns);
            StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(parts);

        } catch (CsvException ex) {

            LOGGER.error("Error mapping Bean to CSV", ex);
        }
    }}