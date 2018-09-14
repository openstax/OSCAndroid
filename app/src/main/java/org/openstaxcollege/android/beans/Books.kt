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
data class Books(
	@SerializedName("amazon_coming_soon") val amazonComingSoon: Boolean? = null,
	@SerializedName("amazon_blurb") val amazonBlurb: String? = null,
	@SerializedName("bookstore_link") val bookstoreLink: String? = null,
	val subject: String? = null,
	@SerializedName("ibook_link") val ibookLink: String? = null,
	val title: String? = null,
	@SerializedName("bookstore_blurb")val bookstoreBlurb: String? = null,
	@SerializedName("coming_soon") val comingSoon: Boolean? = null,
	val urls: List<List<String?>?>? = null,
	@SerializedName("salesforce_name") val salesforceName: String? = null,
	@SerializedName("low_resolution_pdf_url") val lowResolutionPdfUrl: String? = null,
	@SerializedName("is_ap") val isAp: Boolean? = null,
	@SerializedName("saleforce_abbreviation") val salesforceAbbreviation: String? = null,
	val id: Int? = null,
	val slug: String? = null,
	@SerializedName("cover_url") val coverUrl: String? = null,
	@SerializedName("amazon_price") val amazonPrice: Double? = null,
	@SerializedName("webview_link") val webviewLink: String? = null,
	@SerializedName("concept_coach_link") val conceptCoachLink: String? = null,
	@SerializedName("high_resolution_pdf_url") val highResolutionPdfUrl: String? = null,
	@SerializedName("comp_copy_available") val compCopyAvailable: Boolean? = null,
	@SerializedName("ibook_link_volume_2") val ibookLinkVolume2: String? = null,
	@SerializedName("amazon_link") val amazonLink: String? = null,
	@SerializedName("bookstore_coming_soon") val bookstoreComingSoon: Boolean? = null,
	@SerializedName("bookshare_link") val bookshareLink: String? = null
)
