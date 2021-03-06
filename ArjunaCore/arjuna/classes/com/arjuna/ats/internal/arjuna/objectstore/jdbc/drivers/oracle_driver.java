/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
/*
 * $Id: oracle_driver.java 2342 2006-03-30 13:06:17Z  $
 *
 * Copyright (c) 2001 Hewlett-Packard Company
 * Hewlett-Packard Company Confidential
 * Copyright (c) 2004 Arjuna Technologies Limited
 *
 * $Project: ArjunaCore$
 * $Revision: 2342 $
 * $Date: 2006-03-30 14:06:17 +0100 (Thu, 30 Mar 2006) $
 * $Author: $
 */

/*
 * Note: This impl has come from HP-TS-2.2 via. HP-MS 1.0
 */

/*
 * JDBC store implementation driver-specific code.
 * This version for Oracle 8.1/9.* JDBC Drivers (OCI or Thin) ONLY.
 */
package com.arjuna.ats.internal.arjuna.objectstore.jdbc.drivers;

import java.sql.Connection;
import java.sql.SQLException;

import com.arjuna.ats.internal.arjuna.objectstore.jdbc.JDBCImple_driver;

public class oracle_driver extends JDBCImple_driver {

	@Override
	protected String getObjectStateSQLType() {
		return "BLOB";
	}

	@Override
	public int getMaxStateSize() {
		// Oracle BLOBs should be OK up to > 4 GB, but cap @ 10 MB for
		// testing/performance:
		return 1024 * 1024 * 10;
	}

	@Override
	protected void checkCreateTableError(SQLException ex) throws SQLException {
		if (!ex.getSQLState().equals("42000") && ex.getErrorCode() != 955) {
			throw ex;
		}
	}

	@Override
	protected void checkDropTableException(Connection connection,
			SQLException ex) throws SQLException {
		if (!ex.getSQLState().equals("42000") && ex.getErrorCode() != 942) {
			throw ex;
		}
	}
}
