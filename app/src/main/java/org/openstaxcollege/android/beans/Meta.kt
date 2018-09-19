/**
 * Copyright (c) 2018 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.beans

import com.google.gson.annotations.SerializedName

/**
 * Data class for json from openstax CMS
 *
 * @author Ed Woodward
 */
data class Meta(
    @SerializedName("detail_url") val detailUrl: String? = null,
    val parent: Parent? = null,
    @SerializedName("show_in_menus") val showInMenus: Boolean? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("first_published_at") val firstPublishedAt: String? = null,
    val type: String? = null,
    @SerializedName("search_description") val searchDescription: String? = null,
    val slug: String? = null,
    @SerializedName("seo_title") val seoTitle: String? = null
)
