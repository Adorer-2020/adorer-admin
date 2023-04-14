/**
 * 生成随机 uuid
 */
export function getUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c =>
        (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
    )
}

/**
 * list 数组转为 tree
 */
export function list2Tree(list = []) {
    return getChildren({id: 0}, list);

    /**
     * 查找子节点
     * @param root  根节点
     * @param list  list 数据
     */
    function getChildren(root, list) {
        return list.filter(item => item.parentId === root.id)
            .map(item => {
                item.children = getChildren(item, list);
                return item;
            })
            .sort((i1, i2) => i1.sort - i2.sort);
    }
}