<template>
    <el-table
                highlight-current-row:true
                :data="tableData"
                style="width: 100%"
                @row-click="handleEdit"
                >
            <el-table-column
                    prop="institutionId"
                    label="组织id"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.theme"
                    label="活动主题"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.startTime"
                    label="开始时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.endTime"
                    label="结束时间"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.address"
                    label="活动地点"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.req"
                    label="人员要求"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.description"
                    label="活动概述"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="activity.tip"
                    label="活动标签"
                    align="center">
            </el-table-column>
            <el-table-column
                    align="right">
                <el-row>
                    <el-button type="primary" @click="agree()">通过</el-button>
                </el-row>
            
                <el-row>
                    <el-button type="danger" @click="reject()">驳回</el-button>
                </el-row>
            </el-table-column>
    </el-table>
</template>

<script>
import axios from 'axios';

    export default {
        name : 'Activity',
        mounted(){
            this.select();
        },
        data(){
            return{
                institutionId:'',
                activity:{
                    theme:'',
                    startTime:'',
                    endTime:'',
                    address:'',
                    req:'',
                    description:'',
                    recruitNumber: 0,
                    tip:''
                },
                tableData:[
                    {
                        institutionId:'0',
                        activity:{
                            theme:'123',
                            startTime:'2333',
                            endTime:'21123',
                            address:'23123',
                            req:'31231',
                            description:'2312312',
                            tip:'qwqwqw'
                        }
                    },
                    {
                        institutionId:'1',
                        theme:'0',
                        startTime:'0',
                        endTime:'0',
                        address:'0',
                        req:'0',
                        description:'0',
                        tip:'0'
                    }
                ]
            }
        },
        methods:{
            handleEdit (row) {
                //this.activity = row;
                console.log(row);
                this.institutionId = row.institutionId;
                this.activity.theme = row.activity.theme;
                this.activity.startTime = row.activity.startTime;
                this.activity.endTime = row.activity.endTime;
                this.activity.address = row.activity.address;
                this.activity.description = row.activity.description;
                this.activity.req = row.activity.req;
                this.activity.tip = row.activity.tip;
                
                console.log(this.activity);


                //此时就能拿到整行的信息  
            },
            select() {
                axios.get("/activity/ratify")
                    .then(resp => {
                    
                    //设置表格数据
                    console.log(resp.data)
                    this.tableData = resp.data.data;
                })
            },
            agree(){
                this.$confirm('是否通过该活动', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {
                        //console.log("activity:"+this.activity),
                        let url = "/activity/ratifyOk/"+this.institutionId
                        axios.post(url,this.activity)
                            .then(resp =>{
                                if (resp.data.code === 0) {
                                    this.dialogVisible1 = false;
                                    this.select();
                                    this.$message({
                                        message: '通过成功',
                                        type: 'success'
                                        })
                                } else {
                                    this.$message({
                                        message: '通过失败',
                                        type: 'warning'
                                    })
                                }
                            })
                    }).catch(() => {
                        this.$message({
                        type: 'info',
                        message: '已取消通过'
                    });          
                });
            },
            reject(){
                
                this.$confirm('是否驳回该活动', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {
                        axios.delete("/activity/ratifyFalse/"+this.institutionId,{data:this.activity})
                            .then(resp =>{
                                if (resp.data.code === 0) {
                                    this.dialogVisible1 = false;
                                    this.select();
                                    this.$message({
                                        message: '驳回成功',
                                        type: 'success'
                                    })
                                } else {
                                    this.$message({
                                        message: '驳回失败',
                                        type: 'warning'
                                    })
                                }
                            })
                    }).catch(() => {
                        this.$message({
                        type: 'info',
                        message: '已取消驳回'
                    });          
                });
            }
        }
    }
</script>