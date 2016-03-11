package ua.skillsup.gea.phonebook.util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * JAXB не знает как сконвертировать тип LocalDate в XML.
 * Поэтому мы должны предоставить собственный класс LocalDateAdapter и определить процесс конвертации вручную.
 *
 * Adapter (for JAXB) to convert between the LocalDate and the ISO 8601
 * String representation of the date such as '2012-12-03'.
 *
 */
/**
 * Created by Flibberty on 08.01.2016.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

        @Override
        public LocalDate unmarshal(String v) throws Exception {
            return LocalDate.parse(v);
        }

        @Override
        public String marshal(LocalDate v) throws Exception {
            return v.toString();
        }

}
