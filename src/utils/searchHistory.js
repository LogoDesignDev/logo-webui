export const SEARCH_HISTORY_KEY = {
  PROD: 'prodSearchHistory',
  DESIGNER: 'designerSearchHistory'
}

export class SearchHistory {
  constructor (key) {
    this.key = key
    this.history = []

    this.decode()
  }

  /**
   * 清空所有搜索记录
   */
  delAllHistory () {
    window.localStorage.removeItem(this.key)
    this.history = []
  }

  /**
   * 删除某个记录
   */
  delHistory (index) {
    this.history.splice(index, 1)
    // 删除之后重新拼接存储
    let str = ''
    this.history.forEach((item) => {
      str = str + item + ';'
    })
    window.localStorage.setItem(this.key, str)
  }

  /**
   * 增加一条记录（如果已存在则不重复添加）
   */
  addHistory (text) {
    this.history.unshift(text);
    this.history = Array.from([...new Set(this.history)])
    // 重新拼接存储
    let str = ''
    this.history.forEach((item) => {
      str = str + item + ';'
    })
    window.localStorage.setItem(this.key, str)
  }

  /**
   * 解析从localstorage中取出的历史搜索记录串
   */
  decode () {
    let temp = ''
    const str = window.localStorage.getItem(this.key) || ''

    this.history = []
    for (let i = 0; i < str.length; i++) {
      if (str[i] === ';') {
        this.history.push(temp)
        temp = ''
      } else {
        temp = temp + str[i]
      }
    }
  }
}
