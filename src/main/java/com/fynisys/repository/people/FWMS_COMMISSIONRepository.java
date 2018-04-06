package com.fynisys.repository.people;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fynisys.model.people.FWMS_COMMISSION;


@Repository
public interface FWMS_COMMISSIONRepository extends PagingAndSortingRepository<FWMS_COMMISSION, Long> {
	
	/*
	 * Without paging
	 */
	
	@Query("select t from FWMS_COMMISSION t ")
	public List<FWMS_COMMISSION> findAllCommsion();
	
	@Query("select t from FWMS_COMMISSION t where t.WMS_STATUS like 'Approved' ")
	public List<FWMS_COMMISSION> findAllAPPROVEDCommsion();
	
	@Query("select t from FWMS_COMMISSION t where t.WMS_STATUS like 'Not Approved' ")
	public List<FWMS_COMMISSION> findAllUNAPPROVEDCommsion();

	
	/*
	 * With searching
	 */
	@Query("select t from FWMS_COMMISSION t where (t.WMS_FUND IN (:fundids) OR "
			+ "t.WMS_CLIENT_TYPE IN (:clienttypeids) OR "
			+ "t.WMS_CLIENT_ID IN (:clientids) OR "
			+ "t.WMS_BROKER IN (:brokerids) OR "
			+ "t.WMS_STOCK_EXCHANGE_ID IN (:stockids) OR "
			+ "t.WMS_ASSET_TYPE_ID IN (:assetids))")
	public List<FWMS_COMMISSION> findAllCommsion_search(
			@Param("fundids") List<Long> fundids,
			@Param("clienttypeids") List<Long> clienttypeids,
			@Param("clientids") List<Long> clientids,
			@Param("brokerids") List<Long> brokerids,
			@Param("stockids") List<Long> stockids,
			@Param("assetids") List<Long> assetids
			);
	
	@Query("select t from FWMS_COMMISSION t where (t.WMS_FUND IN (:fundids) OR "
			+ "t.WMS_CLIENT_TYPE IN (:clienttypeids) OR "
			+ "t.WMS_CLIENT_ID IN (:clientids) OR "
			+ "t.WMS_BROKER IN (:brokerids) OR "
			+ "t.WMS_STOCK_EXCHANGE_ID IN (:stockids) OR "
			+ "t.WMS_ASSET_TYPE_ID IN (:assetids)) AND t.WMS_STATUS like 'Approved'")
	public List<FWMS_COMMISSION> findAllCommsion_search_APPROVED(
			@Param("fundids") List<Long> fundids,
			@Param("clienttypeids") List<Long> clienttypeids,
			@Param("clientids") List<Long> clientids,
			@Param("brokerids") List<Long> brokerids,
			@Param("stockids") List<Long> stockids,
			@Param("assetids") List<Long> assetids
			);
	
	@Query("select t from FWMS_COMMISSION t where (t.WMS_FUND IN (:fundids) OR "
			+ "t.WMS_CLIENT_TYPE IN (:clienttypeids) OR "
			+ "t.WMS_CLIENT_ID IN (:clientids) OR "
			+ "t.WMS_BROKER IN (:brokerids) OR "
			+ "t.WMS_STOCK_EXCHANGE_ID IN (:stockids) OR "
			+ "t.WMS_ASSET_TYPE_ID IN (:assetids)) AND t.WMS_STATUS like 'Not Approved'")
	public List<FWMS_COMMISSION> findAllCommsion_search_UNAPPROVED(
			@Param("fundids") List<Long> fundids,
			@Param("clienttypeids") List<Long> clienttypeids,
			@Param("clientids") List<Long> clientids,
			@Param("brokerids") List<Long> brokerids,
			@Param("stockids") List<Long> stockids,
			@Param("assetids") List<Long> assetids
			);
	
}
