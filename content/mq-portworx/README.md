# Portworx Storage for IBM MQ

Starting from CP4I 2020.3 Portworx Storage Version 2.5.5 is [offically supported](https://www.ibm.com/support/knowledgecenter/en/SSGT7J_20.3/install/sysreqs.html#icip_sysreqs__file)

However, for `MultiInstance` QueManager's where there is a requirment for *Shared* File Storage the default Portworx `StorageClass` will not be sufficient:

```
kind: StorageClass
metadata:
  labels:
    app.kubernetes.io/instance: portworx
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: portworx-portworx
    helm.sh/chart: portworx-1.0.21
  name: portworx-shared-sc
parameters:
  repl: "3"
  shared: "true"
provisioner: kubernetes.io/portworx-volume
reclaimPolicy: Delete
volumeBindingMode: Immediate
```

You must enable `nfsv_4` to work with `MultiInstance` QM's like so:

```
kind: StorageClass
apiVersion: storage.k8s.io/v1
metadata:
  name: portworx-shared-sc3
provisioner: kubernetes.io/portworx-volume
parameters:
  io_profile: db_remote
  nfs_v4: 'true'
  repl: '3'
  sharedv4: 'true'
reclaimPolicy: Delete
volumeBindingMode: Immediate
```
A major advantage of working with `Portworx Storage` is that it can replicate data across `Availability Zones` this means if you have a `Multi AZ` Cluster Kubernetes will schedule the Active/Passive pod pair into different Zones and they can still share the same `PVC's` for failover since the `PVC's` backing the `MultiInstance` pods will be replicated across `Availability Zones`