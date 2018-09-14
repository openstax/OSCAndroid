/**
 * Copyright (c) 2018 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.beans

/**
 * Data class for json from openstax CMS
 *
 * @author Ed Woodward
 */
data class Parent(
    val meta: Meta? = null,
    val id: Int? = null,
    val title: String? = null
)
