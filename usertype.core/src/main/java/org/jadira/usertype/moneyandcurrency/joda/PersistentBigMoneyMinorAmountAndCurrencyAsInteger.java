/*
 *  Copyright 2010, 2011, 2012 Christopher Pheby
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
package org.jadira.usertype.moneyandcurrency.joda;

import org.jadira.usertype.moneyandcurrency.joda.columnmapper.IntegerColumnCurrencyUnitMapper;
import org.jadira.usertype.moneyandcurrency.joda.columnmapper.LongLongColumnMapper;
import org.jadira.usertype.spi.shared.AbstractMultiColumnUserType;
import org.jadira.usertype.spi.shared.ColumnMapper;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

/**
 * Persists the minor amount and currency from a BigMoney instance
 */
public class PersistentBigMoneyMinorAmountAndCurrencyAsInteger extends AbstractMultiColumnUserType<BigMoney> {

	private static final long serialVersionUID = -3990523657883978202L;

	private static final ColumnMapper<?, ?>[] COLUMN_MAPPERS = new ColumnMapper<?, ?>[] { new IntegerColumnCurrencyUnitMapper(), new LongLongColumnMapper<Long>() };

    private static final String[] PROPERTY_NAMES = new String[]{ "currencyUnit", "amountMinor" };
	
	@Override
	protected ColumnMapper<?, ?>[] getColumnMappers() {
		return COLUMN_MAPPERS;
	}

    @Override
    protected BigMoney fromConvertedColumns(Object[] convertedColumns) {

        CurrencyUnit currencyUnitPart = (CurrencyUnit) convertedColumns[0];
        Long amountMinorPart = (Long) convertedColumns[1];
        BigMoney money = BigMoney.ofMinor(currencyUnitPart, amountMinorPart);

        return money;
    }

    @Override
    protected Object[] toConvertedColumns(BigMoney value) {

        return new Object[] { value.getCurrencyUnit(), value.getAmountMinorLong() };
    }
    
    @Override
	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}
}
