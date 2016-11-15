/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.openstaxcollege.android.logic;

import java.util.HashMap;

/**
 * Testable class for business logic
 *
 * @author Ed Woodward
 *
 */
public class WebviewLogic
{
    public static HashMap<String, String> bookPDFMap;

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
        bookPDFMap = new HashMap<>();

        bookPDFMap.put("College Physics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/College_Physics-LR.pdf");
        bookPDFMap.put("Concepts of Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Concepts_of_Biology-LR.pdf");
        bookPDFMap.put("Principles of Microeconomics for AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Principles_of_Microeconomics_for_AP_Courses-LR.pdf");
        bookPDFMap.put("U.S. History","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/U.S._History-LR.pdf");
        bookPDFMap.put("University Physics Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/UniversityPhysicsVolume3-LR.pdf");
        bookPDFMap.put("Biology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Principles_of_Economics-LR.pdf");
        bookPDFMap.put("Algebra and Trigonometry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Algebra_and_Trigonometry-LR.pdf");
        bookPDFMap.put("Prealgebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Prealgebra-LR.pdf");
        bookPDFMap.put("Calculus Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Calculus_Volume_2-LR.pdf");
        bookPDFMap.put("American Government","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/American_Government_-_LR.pdf");
        bookPDFMap.put("Microeconomics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Principles_of_Microeconomics-LR.pdf");
        bookPDFMap.put("Principles of Macroeconomics for AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Principles_of_Macroeconomics_for_AP_Courses-LR.pdf");
        bookPDFMap.put("Astronomy","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Astronomy-LR.pdf");
        bookPDFMap.put("Chemistry","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Chemistry-LR.pdf");
        bookPDFMap.put("College Physics For AP® Courses","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/CollegePhysicsforAPCourses-LR.pdf");
        bookPDFMap.put("Introduction to Sociology 2e","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Sociology_2e-LR.pdf");
        bookPDFMap.put("Microbiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Microbiology-LR.pdf");
        bookPDFMap.put("College Algebra","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/College_Algebra-LR.pdf");
        bookPDFMap.put("Introductory Statistics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Introductory_Statistics-LR.pdf");
        bookPDFMap.put("Psychology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Psychology-LR.pdf");
        bookPDFMap.put("Chemistry: Atoms First","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Chemistry_Atoms_First-LR.pdf");
        bookPDFMap.put("University Physics Volume 2","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/University_Physics_Volume_2-LR_20161006.pdf");
        bookPDFMap.put("Calculus Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Calculus_Volume_1-LR.pdf");
        bookPDFMap.put("Precalculus","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/OpenStax_Precalc_2015_v2-LR.pdf");
        bookPDFMap.put("University Physics Volume 1","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/University_Physics_Volume_1-LR.pdf");
        bookPDFMap.put("Anatomy & Physiology","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Anatomy_and_Physiology-LR.pdf");
        bookPDFMap.put("Macroeconomics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Principles_of_Macroeconomics-LR.pdf");
        bookPDFMap.put("Calculus Volume 3","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Calculus_Voume_3-LR.pdf");
        bookPDFMap.put("Principles of Economics","https://d3bxy9euw4e147.cloudfront.net/oscms-prodcms/media/documents/Principles_of_Economics-LR.pdf");

    }


}
