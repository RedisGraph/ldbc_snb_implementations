import json
import sys
import humanize
import datetime

queries = {
    "LdbcUpdate1AddPerson": "Update,1",
    "LdbcUpdate2AddPostLike": "Update,2",
    "LdbcUpdate3AddCommentLike": "Update,3",
    "LdbcUpdate4AddForum": "Update,4",
    "LdbcUpdate5AddForumMembership": "Update,5",
    "LdbcUpdate6AddPost": "Update,6",
    "LdbcUpdate7AddComment": "Update,7",
    "LdbcUpdate8AddFriendship": "Update,8",
    "LdbcShortQuery1PersonProfile": "Short,1",
    "LdbcShortQuery2PersonPosts": "Short,2",
    "LdbcShortQuery3PersonFriends": "Short,3",
    "LdbcShortQuery4MessageContent": "Short,4",
    "LdbcShortQuery5MessageCreator": "Short,5",
    "LdbcShortQuery6MessageForum": "Short,6",
    "LdbcShortQuery7MessageReplies": "Short,7",
    "LdbcQuery1": "Complex,1",
    "LdbcQuery2":"Complex,2",
    "LdbcQuery3": "Complex,3",
    "LdbcQuery4": "Complex,4",
    "LdbcQuery5": "Complex,5",
    "LdbcQuery6": "Complex,6",
    "LdbcQuery7": "Complex,7",
    "LdbcQuery8": "Complex,8",
    "LdbcQuery9": "Complex,9",
    "LdbcQuery10": "Complex,10",
    "LdbcQuery11": "Complex,11",
    "LdbcQuery12": "Complex,12",
    "LdbcQuery13": "Complex,13",
    "LdbcQuery14": "Complex,14",
}

pruned_metrics = {}
total_ops_sec = 0
total_count = 0
total_duration = 0
with open(sys.argv[1]) as json_file:
    data = json.load(json_file)
    total_ops_sec = data['throughput']
    total_count = data['total_count']
    total_duration = data['total_duration']
    for metric in data['all_metrics']:
        count = metric["count"]
        name = metric["name"]
        unit = metric["unit"]
        factor = 1.0
        if unit == "MICROSECONDS":
            factor = 1000.0
        q50 = metric["run_time"]["50th_percentile"] / factor
        q95 = metric["run_time"]["95th_percentile"] / factor
        q99 = metric["run_time"]["99th_percentile"] / factor
        pruned_metrics[name]="{query},{count},{q50},{q95},{q99}".format(query=queries[name],count=count,q50=q50,q95=q95,q99=q99)


for query_name in queries:
        row_txt = "{query},-,-,-,-" .format(query=queries[query_name])
        if query_name in pruned_metrics:
            row_txt=pruned_metrics[query_name]
        print(row_txt)
print("")
print("Duration,Operations,Throughput,% above threshold")
print("{},{},{},NA".format(humanize.naturaldelta(datetime.timedelta(milliseconds=total_duration)),total_count,total_ops_sec))

print("----------------------------------------------------")
print("filename {f}".format(f=sys.argv[1]))
print("----------------------------------------------------")