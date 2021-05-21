package com.anjanda.letsmeet.map.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.map.service.StoreService;
import com.anjanda.letsmeet.repository.dto.Store;
import com.anjanda.letsmeet.repository.dto.StoreReview;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @Date 2021. 2. 1.
 * @Team AnJanDa
 * @author 임호빈
 * @deploy : 김동빈
 * @Project : backend
 * @Function : 가게 관련 컨트롤러 클래스
 * @Description : 
 *
 */

@RestController
@RequestMapping(value="/map")
public class StoreController {

	/* 가게 객체 불러오기 */
	@Autowired
	private StoreService storeService;
	
	/* R :: '가게 이름'으로 가게 조회 */
	@ApiOperation(value="가게명으로 조회", notes="가게이름을 이용해서 조회")
	@GetMapping("/storename")
	public List<Store> StoreByName(String sName) throws Exception {
		
			System.out.println("가게명 받아오기");
			System.out.println("data size : " + storeService.StoreByName(sName).size());
			System.out.println("data size : " + storeService.StoreByName(sName).get(0));
			
			return storeService.StoreByName(sName);
		
	}
	
	@GetMapping("/storedetail")
	public List<Store> StoreByDetail(String detail,String x, String y) throws Exception {
		
			System.out.println("가게명 받아오기");
			System.out.println("data size : " + storeService.StoreByDetail(detail,x,y).size());
			System.out.println("data size : " + storeService.StoreByDetail(detail,x,y).get(0));
			
			return storeService.StoreByDetail(detail,x,y);
		
	}
	
	/* C :: 리뷰 생성 */
	@PostMapping("/add")
	public ResponseEntity<Integer> createStore(Store store) throws Exception {
		
		if(storeService.StoreByName(store.getsName()).size()>=1) {
			return new ResponseEntity<Integer>(storeService.StoreByName(store.getsName()).get(0).getsNo(),HttpStatus.OK);
		}else {
			storeService.createStore(store);
			return new ResponseEntity<Integer>(storeService.StoreByName(store.getsName()).get(0).getsNo(),HttpStatus.OK);

		}

	}
	
	/* R :: '좌표 값'으로 가게 조회 */
	@ApiOperation(value="좌표로 조회", notes="좌표를 이용해서 조회")
	@GetMapping("/point")
	public Store StoreByPoint(String lat, String lng) throws Exception{

			System.out.println("좌표값 받아오기");
			System.out.println("data name : " + storeService.StoreByPoint(lat,lng).getsName());
			
			return storeService.StoreByPoint(lat,lng);

	}
	
	/* R :: '스토어 넘버'으로 가게 조회 */
	@ApiOperation(value="스토어 넘버로 조회", notes="스토어 넘버를 이용해서 조회")
	@GetMapping("/sno")
	public Store StoreByNo(int sno) throws Exception{

			System.out.println("좌표값 받아오기");
			System.out.println("data name : " + storeService.StoreByNo(sno).getsName());
			
			return storeService.StoreByNo(sno);

	}

	/* R :: '중간 좌표 값' 으로 반경 내 가게 조회 */
	@ApiOperation(value="중간 좌표로 조회", notes="중간 좌표를 이용해서 조회")
	@GetMapping("/midpoint")
	public List<Store> StoreByMidPoint(Store store) throws Exception{
		
			System.out.println("중간 좌표값 받아오기");
			System.out.println("data size : " + storeService.StoreByMidPoint(store).get(0));

			return storeService.StoreByMidPoint(store);

	}
	

}
