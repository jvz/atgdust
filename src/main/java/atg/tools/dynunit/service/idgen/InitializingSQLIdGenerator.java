/*
 * Copyright 2013 Matt Sicker and Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package atg.tools.dynunit.service.idgen;

import atg.nucleus.ServiceException;
import atg.service.idgen.SQLIdGenerator;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * This IDGenerator is intended to be used by unit tests. It will manage it's
 * own database schema rather than assuming the tables already exist. Otherwise
 * it's the same functionality as the SQLIdGenerator.
 *
 * @author adamb
 * @version $Id: //test/UnitTests/base/main/src/Java/atg/service/idgen/
 *          InitializingSQLIdGenerator.java#1 $
 */
public class InitializingSQLIdGenerator
        extends SQLIdGenerator
        implements InitializingIdGenerator {

    private IdGeneratorInitializer mInitializer;

    /**
     * The SQL statement required to create the table used by this component.
     */
    private static final String CREATE_STATEMENT = " create table das_id_generator ("
                                                  + "id_space_name   varchar(60) not null,"
                                                  + "seed    numeric(19,0)   not null,"
                                                  + " batch_size      integer not null,"
                                                  + " prefix  varchar(10),"
                                                  + " suffix  varchar(10),"
                                                  + "primary key (id_space_name)) ";

    /**
     * Ensures that the required tables for this id generator exist.
     */
    @Override
    public void doStartService()
            throws ServiceException {
        if ( mInitializer == null ) {
            mInitializer = new IdGeneratorInitializer(this);
        }
        try {
            mInitializer.initialize();
        } catch ( SQLException e ) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns the create statement appropriate for the current database
     */
    @NotNull
    public String getCreateStatement() {
        // TODO Add DBCheck and return DB2 syntax
        return CREATE_STATEMENT;
    }
}
