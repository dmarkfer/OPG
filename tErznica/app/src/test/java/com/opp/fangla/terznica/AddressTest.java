package com.opp.fangla.terznica;

import com.opp.fangla.terznica.data.entities.Address;

import org.junit.Test;


public class AddressTest {


    public Address parseAddressIsCorrect (String addr) {

        Address address ;
        String[] tmp = addr.split(",|\\.");


        switch (tmp.length){
            case 1:
                address = setAddress(tmp[0],null,null,null,null);
                break;
            case 2:
                address = setAddress(tmp[0],tmp[1],null,null,null);
                break;
            case 3:
                address = setAddress(tmp[0],tmp[1],tmp[2],null,null);
                break;
            case 4:
                address = setAddress(tmp[0],tmp[1],tmp[2],tmp[3],null);
                break;
            case 5:
                address = setAddress(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]);
                break;
            default:
                address = new Address();

        }

        return address;
    }

    private static Address setAddress (String street, String num,String townNum ,String town, String state) {
        Address address = new Address();
        address.setState(state);
        address.setTown(town);
        address.setStreet(street);
        address.setNumber(num);
        address.setTownNum(townNum);

        return address;
    }

    @Test
    public void testParser () {

        Address addr = parseAddressIsCorrect("Hrvatska");
        System.out.println(addr.toString());

        addr = parseAddressIsCorrect("Zagreb,Hrvatska");
        System.out.println(addr.toString());

        addr = parseAddressIsCorrect("Opatovina ul.,10000,Zagreb,Hrvatska");
        System.out.println(addr.toString());

        addr = parseAddressIsCorrect("Lanište ul. 3c, 10020,Zagreb,Hrvatska");
        System.out.println(addr.toString());


    }
}
