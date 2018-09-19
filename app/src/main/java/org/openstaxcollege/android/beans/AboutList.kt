/**
 * Copyright (c) 2018 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.beans

import java.util.ArrayList

/**
 * Data class for about.json file
 *
 * @author Ed Woodward
 */
data class AboutList(
    val aboutList: ArrayList<About>
)