/*
 *  Copyright 2010, 2011 Christopher Pheby
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.jadira.usertype.dateandtime.joda;

import java.sql.Date;

import org.hibernate.SessionFactory;
import org.hibernate.usertype.ParameterizedType;
import org.jadira.usertype.dateandtime.joda.columnmapper.DateColumnLocalDateMapper;
import org.jadira.usertype.spi.shared.AbstractParameterizedUserType;
import org.jadira.usertype.spi.shared.ConfigurationHelper;
import org.jadira.usertype.spi.shared.IntegratorConfiguredType;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

/**
 * Persist {@link LocalDate} via Hibernate. This type shares database
 * representation with {@link org.joda.time.contrib.hibernate.PersistentLocalDate}
 * 
 * The type is stored using UTC timezone.
 *
 * Alternatively provide the 'databaseZone' parameter in the {@link DateTimeZone#forID(String)} format
 * to indicate the zone of the database. 
 * N.B. To use the zone of the JVM for the database zone you can supply 'jvm'
 */
public class PersistentLocalDate extends AbstractParameterizedUserType<LocalDate, Date, DateColumnLocalDateMapper> implements ParameterizedType, IntegratorConfiguredType {

	private static final long serialVersionUID = 2918856421618299370L;

	@Override
	public void applyConfiguration(SessionFactory sessionFactory) {
		
		super.applyConfiguration(sessionFactory);

        DateColumnLocalDateMapper columnMapper = (DateColumnLocalDateMapper) getColumnMapper();

        String databaseZone = null;
        if (getParameterValues() != null) {
        	databaseZone = getParameterValues().getProperty("databaseZone");
        }
		if (databaseZone == null) {
			databaseZone = ConfigurationHelper.getProperty("databaseZone");
		}
		
        if (databaseZone != null) {
            if ("jvm".equals(databaseZone)) {
                columnMapper.setDatabaseZone(null);
            } else {
                columnMapper.setDatabaseZone(DateTimeZone.forID(databaseZone));
            }
        }
	}
}