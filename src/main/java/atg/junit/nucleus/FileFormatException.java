/**
 * Copyright 2007 ATG DUST Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package atg.junit.nucleus;

import atg.core.exception.ContainerException;

/** A general utility exception thrown if a file is formatted in an unexpected manner.
 *
 * @author m frenzel
 * @version 1.0
 */
public class FileFormatException
    extends ContainerException
{
  /** no arg constructor */
  public FileFormatException() { super(); }

  /* constructor */
  public FileFormatException( String pMsg ) {
      super( pMsg ); }

  /** constructor */
  public FileFormatException( Throwable pErr ) {
      super( pErr ); }

  /** constructor */
  public FileFormatException( String pMsg, Throwable pErr ) {
      super( pMsg, pErr ); }
}
