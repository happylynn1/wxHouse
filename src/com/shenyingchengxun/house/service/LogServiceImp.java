package com.shenyingchengxun.house.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.house.bean.ClientLike;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.bean.VisitLog;
import com.shenyingchengxun.house.dao.ClientLikeDao;
import com.shenyingchengxun.house.dao.VisitLogDao;

@Service("LogServiceImp")
public class LogServiceImp implements LogService {

	@Autowired
	VisitLogDao visitLogDao;

	@Autowired
	ClientLikeDao clientLikeDao;

	public void addVisitLog(String openid, HouseSource hs) {
		// TODO Auto-generated method stub
		if (openid != null) {
			VisitLog vl = new VisitLog(openid, hs.getHouseid(), null,
					hs.getComm(), hs.getTotal(), hs.getRoom(), hs.getTowards(),
					hs.getArea(), hs.getYears(), hs.getFloor(),
					hs.getProperty_type(), hs.getTrading_type());
			visitLogDao.addVisitLog(vl);

			List<VisitLog> visitLogList = new ArrayList<VisitLog>();
			visitLogList = visitLogDao.getMyVisitLogList(openid);
			if (visitLogList.size() > 0) {
				String comm = visitLogList.get(0).getComm() + "|";
				String room = visitLogList.get(0).getRoom();
				if (room != null && room.length() > 0) {
					room = room.split("室")[0] + "|";
				}
				int rentMaxPrice = 0, rentMinPrice = 0, saleMaxPrice = 0, saleMinPrice = 0;
				for (int i = 0; i < visitLogList.size(); i++) {
					String type = visitLogList.get(i).getType();
					String price = visitLogList.get(i).getPrice();
					if ("sale".equals(type)) {
						saleMaxPrice = Integer.parseInt(price);
						saleMinPrice = Integer.parseInt(price);
						break;
					}
				}
				for (int i = 0; i < visitLogList.size(); i++) {
					String type = visitLogList.get(i).getType();
					String price = visitLogList.get(i).getPrice();
					if ("rent".equals(type)) {
						rentMaxPrice = Integer.parseInt(price);
						rentMinPrice = Integer.parseInt(price);
						break;
					}
				}
				for (int i = 1; i < visitLogList.size(); i++) {
					if (!comm.contains(visitLogList.get(i).getComm())) {
						comm += visitLogList.get(i).getComm() + "|";
					}
					String rooms = visitLogList.get(i).getRoom();
					if (rooms != null && rooms.length() > 0) {
						if (!room.contains(rooms.split("室")[0])) {
							room += rooms.split("室")[0] + "|";
						}
					}
					String types = visitLogList.get(i).getType();
					String prices = visitLogList.get(i).getPrice();
					if (prices != null && !prices.equals("null")
							&& prices.length() > 0) {
						int priceInt = Integer.parseInt(prices);
						if (types.equals("sale")) {
							saleMaxPrice = Math.max(saleMaxPrice, priceInt);
							saleMinPrice = Math.min(saleMinPrice, priceInt);
						} else {
							rentMaxPrice = Math.max(rentMaxPrice, priceInt);
							rentMinPrice = Math.min(rentMinPrice, priceInt);
						}
					}
				}
				clientLikeDao.addClientLike(new ClientLike(openid, comm, room,
						saleMaxPrice + "", saleMinPrice + "",
						rentMaxPrice + "", rentMinPrice + ""));
			}
		}
	}
}
