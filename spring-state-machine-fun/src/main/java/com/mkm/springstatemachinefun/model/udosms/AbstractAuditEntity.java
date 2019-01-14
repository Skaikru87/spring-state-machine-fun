//package com.mkm.springstatemachinefun.model.udosms;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat.ISO;
//import org.springframework.data.annotation.*;
//
//import java.util.Date;
//
///**
// * @author r.leszczynski
// *
// */
//@Getter
//@Setter
//public abstract class AbstractAuditEntity {
//
//    /**
//     * Entity identifier
//     */
//    @Id
//    private String id;
//
//    /**
//     * Entity creation date
//     */
//    @DateTimeFormat(iso = ISO.DATE_TIME)
//    @CreatedDate
//    private Date createdAt;
//
//
//    /**
//     * Entity last modification date
//     */
//    @DateTimeFormat(iso = ISO.DATE_TIME)
//    @LastModifiedDate
//    private Date lastModified;
//
//    /**
//     * Entity's creator
//     */
//    @CreatedBy
//    private String createdBy;
//
//    /**
//     * Entity's last modifier
//     */
//    @LastModifiedBy
//    private String lastModifiedBy;
//
//}