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
data class Response(
    val devStandard2Description: String? = null,
    val devStandard1Description: String? = null,
    val devStandard3Description: String? = null,
    val books: List<Books?>? = null,
    val devStandard3Heading: String? = null,
    val devStandardsHeading: String? = null,
    val subjectListHeading: String? = null,
    val devStandard2Heading: String? = null,
    val title: String? = null,
    val devStandard1Heading: String? = null,
    val pageDescription: String? = null
)
