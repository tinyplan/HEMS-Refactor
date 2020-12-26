package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.Site;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteMapper {

     Site getSiteByRoom(@Param("room") String room);

     Integer updateSiteStatus(@Param("siteId") String siteId,
                              @Param("room") String room,
                              @Param("status") Integer status);

     Integer updateSiteCapacity(@Param("room") String room, @Param("capacity") Integer capacity);

     Integer insertSite(Site site);

     Integer insertSites(@Param("siteList") List<Site> siteList);

     List<Site> querySite(@Param("building") String building, @Param("floor") String floor);

     List<Site> querySiteByStatus(@Param("status") Integer status);

     Site querySiteById(@Param("siteId") String siteId);

}
