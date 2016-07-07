/*
 * Copyright (c) 2004-2016 Universidade do Porto - Faculdade de Engenharia
 * Laboratório de Sistemas e Tecnologia Subaquática (LSTS)
 * All rights reserved.
 * Rua Dr. Roberto Frias s/n, sala I203, 4200-465 Porto, Portugal
 *
 * This file is part of Neptus, Command and Control Framework.
 *
 * Commercial Licence Usage
 * Licencees holding valid commercial Neptus licences may use this file
 * in accordance with the commercial licence agreement provided with the
 * Software or, alternatively, in accordance with the terms contained in a
 * written agreement between you and Universidade do Porto. For licensing
 * terms, conditions, and further information contact lsts@fe.up.pt.
 *
 * European Union Public Licence - EUPL v.1.1 Usage
 * Alternatively, this file may be used under the terms of the EUPL,
 * Version 1.1 only (the "Licence"), appearing in the file LICENCE.md
 * included in the packaging of this file. You may not use this work
 * except in compliance with the Licence. Unless required by applicable
 * law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the Licence for the specific
 * language governing permissions and limitations at
 * http://ec.europa.eu/idabc/eupl.html.
 *
 * For more information please see <http://lsts.fe.up.pt/neptus>.
 *
 * Author: Paulo Dias
 * 13/Jan/2005
 */
package pt.lsts.neptus.junit.util.conf;

import junit.framework.TestCase;
import pt.lsts.neptus.NeptusLog;
import pt.lsts.neptus.util.conf.ConfigFetch;

/**
 * @author Paulo Dias
 *
 */
public class ConfigFetchTest extends TestCase
{

    public static void main(String[] args)
    {
        junit.swingui.TestRunner.run(ConfigFetchTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Constructor for ConfigFetchTest.
     * @param arg0
     */
    public ConfigFetchTest(String arg0)
    {
        super(arg0);
    }

    /*
     * Class under test for void ConfigFetch()
     */
    public void testConfigFetch()
    {
    	ConfigFetch.initialize();
    	String lo = ConfigFetch.getLoggingPropertiesLocation();
    	if (lo == null)
    		TestCase.fail("Fail to read config file!");
    	else
    		NeptusLog.pub().info("<###> "+lo);
    }

    /*
     * Class under test for void ConfigFetch(String, boolean)
     */
    public void testConfigFetchStringboolean()
    {
    }

    /*
     * Class under test for void ConfigFetch(String)
     */
    public void testConfigFetchString()
    {
    }

    public void testResolvePath()
    {
    }

    public void testGetErrorDescriptionFile()
    {
    }

    public void testGetLoggingPropertiesLocation()
    {
    }

}
