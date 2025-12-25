package com.center.medical.machine.bean.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.ptr.ByteByReference;

public class IDCardReader {

    public interface RdcardDll extends Library {
        int iOpenPort(int iPort, int iBaud, ByteByReference pErrCode);

        int iClosePort(int iPort);

        int iGetSAMStatus(int iPort, ByteByReference pErrCode);

        int iReadCardBasicInfo(int iPort, int iIfPhoto, ByteByReference pErrCode, BasicInfoStruct pBasicInfo, ByteByReference pPhotoData);
    }

    public static class BasicInfoStruct extends Structure {
        public byte[] name = new byte[30];
        public byte[] sex = new byte[6];
        public byte[] nation = new byte[18];
        public byte[] birth = new byte[16];
        public byte[] address = new byte[70];
        public byte[] id = new byte[36];
        public byte[] issue = new byte[30];
        public byte[] expire = new byte[16];
        public byte[] reserved = new byte[36];
    }

    static {
        System.setProperty("jna.encoding", "GBK");
    }

    public static void main(String[] args) {
        RdcardDll bell = (RdcardDll) Native.loadLibrary("dll/shensi/RdCard.dll", RdcardDll.class);

        ByteByReference pErrCode = new ByteByReference();
        BasicInfoStruct pBasicInfo = new BasicInfoStruct();
        ByteByReference pPhotoData = new ByteByReference();

        int iRet = bell.iOpenPort(1001, 115200, pErrCode);
        if (iRet != 0) {
            System.out.println("Failed to open port, error code: " + pErrCode.getValue());
            return;
        }

        iRet = bell.iGetSAMStatus(1001, pErrCode);
        if (iRet != 0) {
            System.out.println("Failed to get SAM status, error code: " + pErrCode.getValue());
            bell.iClosePort(1001);
            return;
        }

        iRet = bell.iReadCardBasicInfo(1001, 1, pErrCode, pBasicInfo, pPhotoData);
        if (iRet != 0) {
            System.out.println("Failed to read card info, error code: " + pErrCode.getValue());
            bell.iClosePort(1001);
            return;
        }

        String name = new String(pBasicInfo.name).trim();
        String sex = new String(pBasicInfo.sex).trim();
        String nation = new String(pBasicInfo.nation).trim();
        String birth = new String(pBasicInfo.birth).trim();
        String address = new String(pBasicInfo.address).trim();
        String id = new String(pBasicInfo.id).trim();
        String issue = new String(pBasicInfo.issue).trim();
        String expire = new String(pBasicInfo.expire).trim();

        System.out.println("Name: " + name);
        System.out.println("Sex: " + sex);
        System.out.println("Nation: " + nation);
        System.out.println("Birth: " + birth);
        System.out.println("Address: " + address);
        System.out.println("ID: " + id);
        System.out.println("Issue: " + issue);
        System.out.println("Expire: " + expire);

        bell.iClosePort(1001);
    }
}