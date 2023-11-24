
## install
``` shell
# 搜索是否有 minio 镜像
docker search minio
# 有则拉取镜像
docker pull minio/minio

# 先创建minio 文件存放的位置
mkdir -p /opt/docker/minio/data

# 启动并指定端口
docker run \
  -p 5000:5000 \
  -p 5001:5001 \
  --name minio \
  -v /opt/docker/minio/data:/data \
  -e "MINIO_ROOT_USER=admin" \
  -e "MINIO_ROOT_PASSWORD=123456" \
  -d minio/minio server /data --console-address ":5001"
 
# 设置为和 docker 绑定启动,docker 启动则 minio 就启动
docker update --restart=always


```