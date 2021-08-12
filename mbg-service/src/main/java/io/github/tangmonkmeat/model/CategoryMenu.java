package io.github.tangmonkmeat.model;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/18 下午9:49
 */
public class CategoryMenu{

    private Integer id;

    private String name;

    private Integer parentId;

    private String iconUrl;

    private Integer sortOrder;

    private CategoryMenu parent;

    @Override
    public String toString() {
        return "CategoryMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", iconUrl='" + iconUrl + '\'' +
                ", sortOrder=" + sortOrder +
                ", parent=" + parent +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public CategoryMenu getParent() {
        return parent;
    }

    public void setParent(CategoryMenu parent) {
        this.parent = parent;
    }
}
