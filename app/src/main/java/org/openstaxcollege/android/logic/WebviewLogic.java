/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.logic;

import android.util.ArrayMap;

/**
 * Testable class for business logic
 *
 * @author Ed Woodward
 *
 */
public class WebviewLogic
{
    private static ArrayMap<String, String> bookPDFMap;

    public String getBookTitle(String title)
    {
        //String title = webView.getTitle();
        int index1 = title.indexOf(" - ");
        if(index1 > -1)
        {
            int index2 = title.indexOf(" - ", index1 + 3);
            //Log.d("WebViewActivity","1: " + index1 + " 2: " + index2);
            if(index2 == -1)
            {
                return title.substring(0, index1);
            }
            else
            {

                return title.substring(index1 + 3, index2);
            }
        }
        else
        {
            return "";
        }
    }

    public String getPDFUrl(String bookTitle)
    {
        if(bookPDFMap == null)
        {
            setPDFMap();
        }
        return bookPDFMap.get(bookTitle);

    }

    private void setPDFMap()
    {
        bookPDFMap = new ArrayMap<>();

        bookPDFMap.put("College Physics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegePhysics-LR.pdf");
        bookPDFMap.put("Concepts of Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Concepts_Biology-LR.pdf");
        bookPDFMap.put("U.S. History","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/USHistory-LR_RhRtqqq.pdf");
        bookPDFMap.put("University Physics Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume3-LR_aKAl5cS.pdf");
        bookPDFMap.put("Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Biology-LR.pdf");
        bookPDFMap.put("Algebra and Trigonometry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AlgebraAndTrigonometry-LR.pdf");
        bookPDFMap.put("Prealgebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Prealgebra-LR.pdf");
        bookPDFMap.put("Calculus Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume2-LR.pdf");
        bookPDFMap.put("American Government","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AmericanGovernment-LR_mhHsiYl.pdf");
        bookPDFMap.put("Astronomy","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Astronomy-LR_93aO0ti.pdf");
        bookPDFMap.put("Chemistry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Chemistry-LR_WVhXaPt.pdf");
        bookPDFMap.put("College Physics For AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegePhysicsForAPCourses-LR.pdf");
        bookPDFMap.put("Introduction to Sociology 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductionToSociology2e-LR.pdf");
        bookPDFMap.put("Microbiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microbiology-LR.pdf");
        bookPDFMap.put("College Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegeAlgebra-OP.pdf");
        bookPDFMap.put("Introductory Statistics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductoryStatistics-LR_Cf2P8Er.pdf");
        bookPDFMap.put("Psychology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Psychology-LR.pdf");
        bookPDFMap.put("Chemistry: Atoms First","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ChemistryAtomsFirst-LR_z0WmWT5.pdf");
        bookPDFMap.put("University Physics Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume2-LR_FcSQ3eU.pdf");
        bookPDFMap.put("Calculus Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume1-LR_ph2U0uB.pdf");
        bookPDFMap.put("Precalculus","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Precalculus-LR.pdf");
        bookPDFMap.put("University Physics Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume1-LR_wxSLzFS.pdf");
        bookPDFMap.put("Anatomy & Physiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AnatomyandPhysiology-LR.pdf");
        bookPDFMap.put("Calculus Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume3-LR_cnyMRJH.pdf");
        bookPDFMap.put("Elementary Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ElementaryAlgebra-LR.pdf");
        bookPDFMap.put("Intermediate Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntermediateAlgebra-LR.pdf");
        bookPDFMap.put("Principles of Microeconomics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microeconomics2e-LR_gtLfNLW.pdf");
        bookPDFMap.put("Introductory Business Statistics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductoryBusinessStatistics-LR_iSZZ9Cm.pdf");
        bookPDFMap.put("Principles of Economics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Economics2e-LR_BWLGPBe.pdf");
        bookPDFMap.put("Principles of Macroeconomics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Macroeconomics2e-LR_ay7Ki8E.pdf");
        bookPDFMap.put("Principles of Macroeconomics for AP® Courses 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APMacroeconomics2e-LR.pdf");
        bookPDFMap.put("Principles of Microeconomics for AP® Courses 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APMicroeconomics2e-LR.pdf");
        bookPDFMap.put("Biology for AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APBiology-LR_RSBRvKq.pdf");
        bookPDFMap.put("Biology 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Biology2e-LR.pdf");
        bookPDFMap.put("Fizyka dla szkół wyższych Tom 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Fizyka_dla_szkol_wyzszych_tom1.pdf");
        bookPDFMap.put("Fizyka dla szkół wyższych Tom 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Fizyka-dla-szkolwyzszych-tom-2-LR.pdf");
    }


}
