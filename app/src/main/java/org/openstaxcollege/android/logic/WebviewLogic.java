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
    public static ArrayMap<String, String> bookPDFMap;

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
        bookPDFMap.put("Principles of Microeconomics for AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/PrinciplesofMicroeconomicsforAPCourses-LR.pdf");
        bookPDFMap.put("U.S. History","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/U.S_History-LR.pdf");
        bookPDFMap.put("University Physics Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume3-LR.pdf");
        bookPDFMap.put("Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Biology-LR.pdf");
        bookPDFMap.put("Algebra and Trigonometry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AlgebraAndTrigonometry-LR.pdf");
        bookPDFMap.put("Prealgebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Prealgebra-LR.pdf");
        bookPDFMap.put("Calculus Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume2-LR.pdf");
        bookPDFMap.put("American Government","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AmericanGovernment-LR.pdf");
        bookPDFMap.put("Principles of Microeconomics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/PrinciplesofMicroeconomicsforAPCourses-LR.pdf");
        bookPDFMap.put("Principles of Macroeconomics for AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/PrinciplesofMacroeconomicsforAPCourses-LR.pdf");
        bookPDFMap.put("Astronomy","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Astronomy-LR.pdf");
        bookPDFMap.put("Chemistry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Chemistry-LR.pdf");
        bookPDFMap.put("College Physics For AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegePhysicsForAPCourses-LR.pdf");
        bookPDFMap.put("Introduction to Sociology 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntroductionToSociology2e-LR.pdf");
        bookPDFMap.put("Microbiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microbiology-LR.pdf");
        bookPDFMap.put("College Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegeAlgebra-OP.pdf");
        bookPDFMap.put("Introductory Statistics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Statistics-LR.pdf");
        bookPDFMap.put("Psychology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Psychology-LR.pdf");
        bookPDFMap.put("Chemistry: Atoms First","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ChemistryAtomsFirst-LR.pdf");
        bookPDFMap.put("University Physics Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume2-LR.pdf");
        bookPDFMap.put("Calculus Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume1-LR.pdf");
        bookPDFMap.put("Precalculus","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Precalculus-LR.pdf");
        bookPDFMap.put("University Physics Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume1-LR.pdf");
        bookPDFMap.put("Anatomy & Physiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/AnatomyandPhysiology-LR.pdf");
        bookPDFMap.put("Principles of Macroeconomics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/PrinciplesofMacroeconomics-LR.pdf");
        bookPDFMap.put("Calculus Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CalculusVolume3-LR.pdf");
        bookPDFMap.put("Principles of Economics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/PrinciplesofEconomics-LR.pdf");
        bookPDFMap.put("Elementary Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/ElementaryAlgebra-LR.pdf");
        bookPDFMap.put("Intermediate Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/IntermediateAlgebra-LR.pdf");
        bookPDFMap.put("Principles of Microeconomics 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microeconomics2e-LR.pdf");
    }


}
