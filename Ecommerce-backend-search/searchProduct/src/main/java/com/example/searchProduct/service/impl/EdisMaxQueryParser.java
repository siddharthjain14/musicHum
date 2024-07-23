package com.example.searchProduct.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.solr.core.DefaultQueryParser;
import org.springframework.data.solr.core.QueryParserBase;
import org.springframework.data.solr.core.mapping.SolrPersistentEntity;
import org.springframework.data.solr.core.mapping.SolrPersistentProperty;
import org.springframework.data.solr.core.query.AbstractQueryDecorator;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.lang.Nullable;

public class EdisMaxQueryParser extends QueryParserBase<SolrDataQuery> {

    private MappingContext<? extends SolrPersistentEntity<?>, SolrPersistentProperty> mappingContext;

    DefaultQueryParser defaultQueryParser;

    public EdisMaxQueryParser(@Nullable MappingContext<? extends SolrPersistentEntity<?>, SolrPersistentProperty> mappingContext){
        super(mappingContext);
        this.mappingContext = mappingContext;
        this.defaultQueryParser = new DefaultQueryParser(mappingContext);
    }

    @Override
    public SolrQuery doConstructSolrQuery(SolrDataQuery edismaxQuery, Class<?> aClass) {

        SolrQuery target = defaultQueryParser.constructSolrQuery(edismaxQuery, aClass);

        SimpleEdismaxQuery query = null;
        if(edismaxQuery instanceof SimpleEdismaxQuery)
            query = (SimpleEdismaxQuery) edismaxQuery;
        else if(edismaxQuery instanceof AbstractQueryDecorator)
            query = ((SimpleEdismaxQuery)((AbstractQueryDecorator)edismaxQuery).getDecoratedQuery());
        else
            return target;
        target.add("defType", "edismax");
        target.add("qf", query.getQueryField());
        if(query.getMinimumMatch()!=null && !query.getMinimumMatch().isEmpty())
            target.add("mm",query.getMinimumMatch());

        return target;
    }
}
