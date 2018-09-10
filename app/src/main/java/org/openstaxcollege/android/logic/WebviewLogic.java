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

        bookPDFMap.put("College Physics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegePhysics-LR_ZHVTqGv.pdf");
        bookPDFMap.put("Concepts of Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ConceptsBiology-LR.pdf");
        bookPDFMap.put("U.S. History","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/USHistory-LR_sOGAySW.pdf");
        bookPDFMap.put("University Physics Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume3-LR.pdf");
        bookPDFMap.put("Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Biology-LR_2ufca7k.pdf");
        bookPDFMap.put("Algebra and Trigonometry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AlgebraAndTrigonometry-LR_jIiGwpf.pdf");
        bookPDFMap.put("Prealgebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Prealgebra-LR_iAcQKVM.pdf");
        bookPDFMap.put("Calculus Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume2-LR_GLQOG8D.pdf");
        bookPDFMap.put("American Government","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AmericanGovernment-LR.pdf");
        bookPDFMap.put("Astronomy","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Astronomy-LR_pbconRH.pdf");
        bookPDFMap.put("Chemistry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Chemistry-LR_urLff6c.pdf");
        bookPDFMap.put("College Physics For AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APCollegePhysics-LR.pdf");
        bookPDFMap.put("Introduction to Sociology 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductionToSociology2e-LR_t4lH53m.pdf");
        bookPDFMap.put("Microbiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microbiology-LR.pdf");
        bookPDFMap.put("College Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegeAlgebra-LR_GoAf7S5.pdf");
        bookPDFMap.put("Introductory Statistics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductoryStatistics-LR_WLztjsS.pdf");
        bookPDFMap.put("Psychology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APCollegePhysics-LR.pdf");
        bookPDFMap.put("Chemistry: Atoms First","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ChemistryAtomsFirst-LR_IMzYB3r.pdf");
        bookPDFMap.put("University Physics Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume2-LR.pdf");
        bookPDFMap.put("Calculus Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume1-LR.pdf");
        bookPDFMap.put("Precalculus","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Precalculus-LR_uFyyhtK.pdf");
        bookPDFMap.put("University Physics Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume1-LR.pdf");
        bookPDFMap.put("Anatomy & Physiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AnatomyandPhysiology-LR_fm0O1Im.pdf");
        bookPDFMap.put("Calculus Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume3-LR.pdf");
        bookPDFMap.put("Elementary Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ElementaryAlgebra-LR_C08YrPA.pdf");
        bookPDFMap.put("Intermediate Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntermediateAlgebra-LR_p4rq5Z2.pdf");
        bookPDFMap.put("Principles of Microeconomics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microeconomics2e-LR.pdf");
        bookPDFMap.put("Introductory Business Statistics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductoryBusinessStatistics-LR.pdf");
        bookPDFMap.put("Principles of Economics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Economics2e-LR.pdf");
        bookPDFMap.put("Principles of Macroeconomics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Macroeconomics2e-LR.pdf");
        bookPDFMap.put("Principles of Macroeconomics for AP® Courses 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APMacroeconomics2e-LR_QzzlR43.pdf");
        bookPDFMap.put("Principles of Microeconomics for AP® Courses 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APMicroeconomics2e-LR_YqdQrhb.pdf");
        bookPDFMap.put("Biology for AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/APBiology-LR.pdf");
        bookPDFMap.put("Biology 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Biology2e-LR_mY0EJOt.pdf");
        bookPDFMap.put("Fizyka dla szkół wyższych Tom 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Fizyka_dla_szkol_wyzszych_tom1.pdf");
        bookPDFMap.put("Fizyka dla szkół wyższych Tom 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Fizyka-dla-szkolwyzszych-tom-2-LR.pdf");
    }


}
