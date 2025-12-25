package com.center.medical.machine.bean.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: mecreg
 * @description:
 * @author: yuane
 * @create: 2020-12-08 15:45
 */
public class PackageItemEntity {
    private String packageId;
    private String packageName;
    private String packageType;
    private Integer betweenSelectNum;
    private Integer groupSelectNum;
    private BigDecimal requiredItemsTotalPrice;
    private List<RequiredItems> requiredItems;
    private List<GroupItems> groupItems;
    private List<BetweenItems> betweenItems;
    private String betweenItemsDescription;
    private List<String> groupIds;
    public BigDecimal getRequiredItemsTotalPrice() {
        return requiredItemsTotalPrice;
    }

    public void setRequiredItemsTotalPrice(BigDecimal requiredItemsTotalPrice) {
        this.requiredItemsTotalPrice = requiredItemsTotalPrice;
    }

    public List<BetweenItems> getBetweenItems() {
        return betweenItems;
    }

    public void setBetweenItems(List<BetweenItems> betweenItems) {
        this.betweenItems = betweenItems;
    }

    public String getBetweenItemsDescription() {
        return betweenItemsDescription;
    }

    public void setBetweenItemsDescription(String betweenItemsDescription) {
        this.betweenItemsDescription = betweenItemsDescription;
    }

    public List<GroupItems> getGroupItems() {
        return groupItems;
    }

    public void setGroupItems(List<GroupItems> groupItems) {
        this.groupItems = groupItems;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Integer getBetweenSelectNum() {
        return betweenSelectNum;
    }

    public void setBetweenSelectNum(Integer betweenSelectNum) {
        this.betweenSelectNum = betweenSelectNum;
    }

    public List<RequiredItems> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<RequiredItems> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public Integer getGroupSelectNum() {
        return groupSelectNum;
    }

    public void setGroupSelectNum(Integer groupSelectNum) {
        this.groupSelectNum = groupSelectNum;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public static class RequiredItems {
        private String itemId;
        private String itemName;
        private String description;
        private BigDecimal price;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

    public static class GroupItems {
        private String group;
        private String groupDescription;
        private List<Item> items;
        private BigDecimal price;
        private Integer groupSelectNum;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getGroupDescription() {
            return groupDescription;
        }

        public void setGroupDescription(String groupDescription) {
            this.groupDescription = groupDescription;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getGroupSelectNum() {
            return groupSelectNum;
        }

        public void setGroupSelectNum(Integer groupSelectNum) {
            this.groupSelectNum = groupSelectNum;
        }
    }

    public static class Item {
        private String group;
        private String itemId;
        private String itemName;
        private String description;
        private BigDecimal price;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }


    public static class BetweenItems {
        private String group;
        private String groupName;
        private List<Item> items;
        private List<String> itemIds;
        private BigDecimal price;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public List<String> getItemIds() {
            return itemIds;
        }

        public void setItemIds(List<String> itemIds) {
            this.itemIds = itemIds;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}