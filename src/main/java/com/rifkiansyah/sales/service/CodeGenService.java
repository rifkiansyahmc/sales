package com.rifkiansyah.sales.service;

import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class CodeGenService
{

    public CodeGenService(){}

    public String genServiceCode( String code, String objMaxCode )
    {
        return genServiceCode(code, objMaxCode, 9);
    }

    public String genServiceCode( String code, String objmaxCode, int substr )
    {
        return genServiceCode(code, objmaxCode, substr, "%08d");
    }

    public String genServiceCode( String code, String objmaxCode, int substr, String format )
    {
        if ( objmaxCode != null ) {
            String maxCode = objmaxCode;
            Long max = Long.parseLong( maxCode.substring( substr ) ) + 1;
            maxCode = String.format(format, max);
            code = code + maxCode;
        }
        else
            code = code + String.format(format, 1);
        return code;
    }

    public String initGen( String code )
    {
        DateFormat df = new SimpleDateFormat( "yy" );
        DateFormat dd = new SimpleDateFormat( "MM" );
        Date today = Calendar.getInstance()
                .getTime();
        String formattedYear = df.format( today );
        String formattedMonth = dd.format( today );
        return code + "-" + formattedYear + formattedMonth + "-";
    }

    public String getCode(String field, String code, TypedQuery<String> query)
    {
        query.setParameter(field, code + "%");
        query.setMaxResults(1);

        String max = null;
        try {
            max = query.getSingleResult();
        } catch ( NoResultException e) {
        }

        if (max != null)
            return genServiceCode( code, max, 12 );
        else
            return genServiceCode( code, null );
    }

    public String getCode(String field, String code, int subStr, TypedQuery<String> query)
    {
        query.setParameter(field, code + "%");
        query.setMaxResults(1);

        String max = null;
        try {
            max = query.getSingleResult();
        } catch ( NoResultException e) {
        }

        System.out.println("FETCH:" + max);
        if (max != null)
            return genServiceCode( code, max, subStr );
        else
            return genServiceCode( code, null );
    }
}
