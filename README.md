简易钱包仪表盘，包含以下功能：
  - ✅ 读取三个JSON文件（currencies.json, live-rates.json, wallet-balance.json）
  - ✅ 根据钱包余额查找支持的币种
  - ✅ 根据汇率数据计算USD价值
  - ✅ 按USD价值排序显示
  - ✅ 支持下拉刷新
  - ✅ 错误处理和加载状态

技术栈：
  - Kotlin + Flow
  - MVVM 的架构模式

项目结构图：
├── AppHelper.kt
├── data
│    ├── local
│    │   └── LocalDataSource.kt         从assets读取JSON文件的数据源
│    └── repository
│        ├── WalletRepository.kt        数据仓库接口和实现
│        └── WalletRepositoryImpl.kt
├── model
│    ├── Currency.kt        币种信息模型
│    ├── LiveRate.kt        实时汇率数据模型
│    ├── WalletBalance.kt   钱包余额模型
│    └── WalletItem.kt      UI显示数据模型
├── ui
│    ├── HomeActivity.kt    主界面，显示总价值和币种列表
│        ├── activity_home.xml: 主界面布局
│    ├── HomeViewModel.kt   使用Flow处理业务逻辑，包括：
                              - 组合三个数据源（币种、汇率、钱包余额）
                              - 计算USD价值
                              - 按USD价值排序
                              - 错误处理和加载状态
│    └── WalletAdapter.kt   RecyclerView适配器
│        ├── item_wallet.xml: 钱包项目布局
└── WalletApp.kt

数据处理逻辑：
  - 从assets读取JSON数据
  - 将币种、汇率、余额数据关联匹配
  - 计算每种币的USD价值 = 余额 × 汇率
  - 按USD价值降序排列
