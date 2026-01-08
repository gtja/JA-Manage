<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
      <el-form-item label="发布产品" prop="releaseProduct">
        <el-input
          v-model="queryParams.releaseProduct"
          placeholder="请输入发布产品"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布应用" prop="releaseApplication">
        <el-input
          v-model="queryParams.releaseApplication"
          placeholder="请输入发布应用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="影响范围" prop="impactScopeId">
        <el-select v-model="queryParams.impactScopeId" placeholder="请选择影响范围" clearable>
          <el-option
            v-for="scope in impactScopeOptions"
            :key="scope.scopeId"
            :label="scope.scopeName"
            :value="scope.scopeId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发布版本" prop="releaseVersion">
        <el-input
          v-model="queryParams.releaseVersion"
          placeholder="请输入发布版本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布服务" prop="releaseService">
        <el-input
          v-model="queryParams.releaseService"
          placeholder="请输入发布服务"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布人" prop="releaseUser">
        <el-input
          v-model="queryParams.releaseUser"
          placeholder="请输入发布人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['version:release:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['version:release:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['version:release:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="releaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="发布ID" align="center" prop="releaseId" width="80" />
      <el-table-column label="发布产品" align="center" prop="releaseProduct" />
      <el-table-column label="发布应用" align="center" prop="releaseApplication" />
      <el-table-column label="影响范围" align="center" prop="impactScopeName" />
      <el-table-column label="发布版本" align="center" prop="releaseVersion" />
      <el-table-column label="发布服务" align="center" prop="releaseService" />
      <el-table-column label="发布人" align="center" prop="releaseUser" />
      <el-table-column label="发布时间" align="center" prop="releaseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.releaseTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['version:release:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['version:release:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="发布产品" prop="releaseProduct">
          <el-input v-model="form.releaseProduct" placeholder="请输入发布产品" />
        </el-form-item>
        <el-form-item label="发布应用" prop="releaseApplication">
          <el-input v-model="form.releaseApplication" placeholder="请输入发布应用" />
        </el-form-item>
        <el-form-item label="影响范围" prop="impactScopeId">
          <el-select v-model="form.impactScopeId" placeholder="请选择影响范围">
            <el-option
              v-for="scope in impactScopeOptions"
              :key="scope.scopeId"
              :label="scope.scopeName"
              :value="scope.scopeId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="发布版本" prop="releaseVersion">
          <el-input v-model="form.releaseVersion" placeholder="请输入发布版本" />
        </el-form-item>
        <el-form-item label="发布服务" prop="releaseService">
          <el-input v-model="form.releaseService" placeholder="请输入发布服务" />
        </el-form-item>
        <el-form-item label="发布人" prop="releaseUser">
          <el-input v-model="form.releaseUser" placeholder="请输入发布人" />
        </el-form-item>
        <el-form-item label="代码分支" prop="codeBranch">
          <el-input v-model="form.codeBranch" placeholder="请输入代码分支" />
        </el-form-item>
        <el-form-item label="发布时间" prop="releaseTime">
          <el-date-picker
            v-model="form.releaseTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择发布时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="发布内容" prop="releaseContent">
          <el-input v-model="form.releaseContent" type="textarea" placeholder="请输入发布内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRelease, getRelease, addRelease, updateRelease, delRelease } from "@/api/version/release"
import { listImpactScope } from "@/api/version/impactScope"

export default {
  name: "VersionPublish",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 发布预告表格数据
      releaseList: [],
      // 影响范围选项
      impactScopeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        releaseProduct: undefined,
        releaseApplication: undefined,
        impactScopeId: undefined,
        releaseVersion: undefined,
        releaseService: undefined,
        releaseUser: undefined,
        createBy: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        releaseProduct: [
          { required: true, message: "发布产品不能为空", trigger: "blur" }
        ],
        releaseApplication: [
          { required: true, message: "发布应用不能为空", trigger: "blur" }
        ],
        impactScopeId: [
          { required: true, message: "影响范围不能为空", trigger: "change" }
        ],
        releaseVersion: [
          { required: true, message: "发布版本不能为空", trigger: "blur" }
        ],
        releaseUser: [
          { required: true, message: "发布人不能为空", trigger: "blur" }
        ],
        releaseTime: [
          { required: true, message: "发布时间不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getImpactScopeOptions()
  },
  methods: {
    /** 查询发布预告列表 */
    getList() {
      this.loading = true
      listRelease(this.queryParams).then(response => {
        this.releaseList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 获取影响范围下拉选项 */
    getImpactScopeOptions() {
      listImpactScope({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.impactScopeOptions = response.rows || []
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        releaseId: undefined,
        releaseProduct: undefined,
        releaseApplication: undefined,
        releaseContent: undefined,
        impactScopeId: undefined,
        codeBranch: undefined,
        releaseTime: undefined,
        releaseVersion: undefined,
        releaseService: undefined,
        releaseUser: undefined
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.releaseId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加发布预告"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const releaseId = row.releaseId || this.ids
      getRelease(releaseId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改发布预告"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.releaseId !== undefined) {
            updateRelease(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addRelease(this.form).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const releaseIds = row.releaseId || this.ids
      this.$modal.confirm('是否确认删除发布预告编号为"' + releaseIds + '"的数据项？').then(() => {
        return delRelease(releaseIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.app-container :deep(.el-select) {
  width: 180px;
}
</style>
